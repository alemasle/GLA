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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.acceis.forum.controlAccessManager.ControleAccessManager;
import fr.acceis.forum.entity.Utilisateur;
import fr.acceis.forum.roles.Invite;
import fr.acceis.forum.roles.Role;

public class AccessFilter implements Filter {

	private final static Logger logger = LogManager.getLogger(AccessFilter.class);

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

		if (session.getAttribute("user") == null || session.getAttribute("utilisateur") == null) { // Nouveau visiteur
			session.setAttribute("user", "invite");
			Role role = new Invite();
			Utilisateur invite = new Utilisateur("invite", "", 0, -1, "", "", role);
			session.setAttribute("utilisateur", invite);
			logger.info("New visitor ip: " + req.getRemoteAddr());
		}

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		String path = req.getServletPath();
		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		if (path.startsWith("css") || path.startsWith("fichiers")) { // Autorisation d'accès aux feuilles css et aux
																		// fichiers comme les avatars utilisateurs.
			chain.doFilter(req, resp);
			return;
		}

		String tmpPath = (String) session.getAttribute("accessWanted");
		if (tmpPath == null || !path.equals("login")) {
			String params = req.getQueryString();
			if (params != null) {
				params = "?" + params;
			} else {
				params = "";
			}
			session.setAttribute("accessWanted", "/forum/" + path + params); // Sauvegarde de la destination voulue par
																				// l'utilisateur en cas de redirection
																				// vers login
		}

		String sourceFrom = (String) session.getAttribute("sourceFrom");
		if (sourceFrom == null) {
			session.setAttribute("sourceFrom", "home"); // Permet de vérifier la provenance de la requete, vérifie
														// l'acces a une page depuis une page valide.
		}

		if (ControleAccessManager.autorize(utilisateur, path)) { // Autorisation d'acces a une url selon l'utilisateur
			if (utilisateur.getRole().getRole().equals("invite")) {
				logger.info("visitor " + req.getRemoteAddr() + " has been autorized to access \"" + path + "\" method: "
						+ req.getMethod());
			} else {
				logger.info(
						"\"" + utilisateur.getLogin() + "\" accesses to \"" + path + "\" method: " + req.getMethod());
			}

			chain.doFilter(req, resp);

		} else { // Refus d'acces a l'url
			if (utilisateur.getLogin().compareTo("invite") == 0) {
				logger.warn("visitor " + req.getRemoteAddr() + " has been refused to access \"" + path
						+ "\", redirected to login");
				resp.sendRedirect("/forum/login");
			} else {
				logger.warn("\"" + utilisateur.getLogin() + "\" is unautorized to access \"" + path
						+ "\", redirected to home");
				resp.sendRedirect("/forum/home");
			}
		}
	}

	@Override
	public void destroy() {

	}

}
