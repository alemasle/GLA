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

	private final String LOGIN = "/forum/login";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
//		if (filterConfig == null) {
//			System.out.println("Erreur config");
//		}

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

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

		if (ControleAccessManager.autorize(utilisateur, path)) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect("/forum/home");
		}
	}

	@Override
	public void destroy() {

	}

}
