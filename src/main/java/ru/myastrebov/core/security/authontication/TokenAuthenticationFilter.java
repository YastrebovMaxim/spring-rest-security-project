package ru.myastrebov.core.security.authontication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token authentication filter, do authentication and set token to response
 * fixme make functional for logout
 * @author Maxim
 */
@Component
public class TokenAuthenticationFilter extends GenericFilterBean {
    private static final String HEADER_TOKEN = "X_ACCESS_TOKEN";
    private static final String HEADER_USERNAME = "X-Username";
    private static final String HEADER_PASSWORD = "X-Password";


    private final AuthenticationService authenticationService;


    @Autowired
    public TokenAuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        boolean authenticated = checkToken(httpRequest, httpResponse);
        if (!authenticated) {
            if (!tryAuthenticate(httpRequest, httpResponse)) {
                debug("Can not authenticate");
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Try authenticate if need
     * @param httpRequest request`
     * @param httpResponse response
     * @return true if authenticated else false
     * @throws IOException
     */
    private boolean tryAuthenticate(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
//        String userName = httpRequest.getHeader(HEADER_USERNAME);
//        String password = httpRequest.getHeader(HEADER_PASSWORD);
        String userName = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");

        return userName != null && password != null && checkUserNameAndPassword(userName, password, httpResponse);
    }

    /**
     * Check that userName and password are existed and if they exist create and set to header new token.
     * @param userName user name
     * @param password password
     * @param httpResponse http response
     * @return true if user name and password are valid
     * @throws IOException
     */
    private boolean checkUserNameAndPassword(String userName, String password, HttpServletResponse httpResponse) throws IOException {
        TokenInfo tokenInfo = authenticationService.authenticate(userName, password);
        if (tokenInfo != null) {
            httpResponse.setHeader(HEADER_TOKEN, tokenInfo.getToken());
            return true;
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid user name or password.");
            return false;
        }
    }

    /**
     * Check token exist in header and if exist it is valid
     * @param request http request
     * @param response http response
     * @return true if existed  token valid else false
     * @throws IOException
     */
    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(HEADER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return false;
        }

        if (authenticationService.checkToken(token)) {
            debug(HEADER_TOKEN + " valid for: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return true;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return false;
        }
    }

    /**
     * logger debug
     * @param message message
     */
    protected void debug(String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }
}
