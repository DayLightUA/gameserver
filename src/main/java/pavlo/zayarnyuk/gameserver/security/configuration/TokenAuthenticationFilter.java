package pavlo.zayarnyuk.gameserver.security.configuration;


//import org.apache.log4j.Logger;

import pavlo.zayarnyuk.gameserver.security.tokenUtils.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenAuthenticationFilter extends GenericFilterBean {


    @Value("${external.url.pattern:#{null}}")
    private String externalUrlPattern;

    @Value("${token.header}")
    private String tokenHeader;

    @Value("${token.uri.param:#{null}}")
    private String accessToken;

    @Autowired
    private TokenTools tokenTools;

    private List<String> external_urls;

    @PostConstruct
    private void setExternalUrls() {
        external_urls = Arrays.asList("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/*", "/login", externalUrlPattern);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (externalUrlPattern != null && !externalUrlPattern.isEmpty()) {
            for (String uri : external_urls) {
                Pattern p = Pattern.compile(uri);
                Matcher m = p.matcher(((HttpServletRequest) request).getRequestURI());
                if (m.find()) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }


        // options is not secured
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            LOGGER.debug("security check - passing method " + RequestMethod.OPTIONS.name());
            chain.doFilter(request, response);
            return;
        }

        // validation token
        String fullToken = httpRequest.getHeader(this.tokenHeader);
        if (StringUtils.isEmpty(fullToken)) {
            fullToken = httpRequest.getParameter(this.accessToken);
            fullToken = "Bearer " + fullToken;
            if (StringUtils.isEmpty(fullToken)) {
                setErrorResponse(response, "No bearer token available");
                return;
            }
        }
        String authToken = fullToken.substring("Bearer".length() + 1, fullToken.length());
//        LOGGER.debug("Security filter activated for " + httpRequest.getRequestURI() + " with token " + authToken);

        boolean valid = tokenTools.isTokenValid(authToken);
        if (!valid) {
            setErrorResponse(response, "Token is not valid");
            return;
        }

        boolean expired = tokenTools.isTokenExpired(authToken);
        if (expired) {
            setErrorResponse(response, "Token is expired");
            return;
        }

        // Set user in context
        UserDetails userDetails = tokenTools.getUserByToken(authToken);
        UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        userPassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
        SecurityContextHolder.getContext().setAuthentication(userPassToken);

//        LOGGER.debug("continue with the chain...");
//        if (LOGGER.isTraceEnabled()){
//            traceSession(request);
//        }
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        chain.doFilter(request, response);
    }

    private void setErrorResponse(ServletResponse response, String msg) throws IOException {
//        LOGGER.warn("Token error - " + msg);
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        ((HttpServletResponse) response).setHeader("WWW-Authenticate", "Bearer realm=\"Service\", error=\"invalid_grant\", error_description=\"" + msg + ".\"");
    }

    private void traceSession(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
//        LOGGER.trace(((HttpServletRequest) request).getRequestURL().toString());
//        LOGGER.trace(((HttpServletRequest) request).getQueryString());
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
//                LOGGER.trace("Header: " + httpRequest.getHeader(headerNames.nextElement()));
            }
        }
    }
}
