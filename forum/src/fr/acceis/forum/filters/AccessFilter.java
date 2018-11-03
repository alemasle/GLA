package fr.acceis.forum.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.acceis.forum.controlAccessManager.ControleAccessManager;
import fr.acceis.forum.entity.Utilisateur;
import fr.acceis.forum.roles.Invite;
import fr.acceis.forum.roles.Role;

public class AccessFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");

		if (session.getAttribute("user") == null || session.getAttribute("utilisateur") == null) {
			session.setAttribute("user", "invite");
			Role role = new Invite();
			Utilisateur invite = new Utilisateur("invite", "", 0, -1, "", "", role);
			session.setAttribute("utilisateur", invite);
		}

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		String path = req.getServletPath();
		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		if (path.startsWith("css") || path.startsWith("fichiers")) {
			chain.doFilter(req, resp);
			return;
		}

		String tmpPath = (String) session.getAttribute("accessWanted");
		if (tmpPath == null || !path.equals("login")) {
			String params = req.getQueryString();
			if (params != null) {
				params = "?" + params;
			}
			session.setAttribute("accessWanted", "/forum/" + path + params);
		}

		if (ControleAccessManager.autorize(utilisateur, path)) {
			chain.doFilter(req, resp);
		} else {
			if (utilisateur.getLogin().compareTo("invite") == 0) {
				resp.sendRedirect("/forum/login");
			} else {
				resp.sendRedirect("/forum/home");
			}
		}
	}

	@Override
	public void destroy() {

	}

}
