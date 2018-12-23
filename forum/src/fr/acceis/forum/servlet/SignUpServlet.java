package fr.acceis.forum.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.acceis.forum.entity.Utilisateur;

public class SignUpServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(SignUpServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		session.setAttribute("sourceFrom", "signup");
		req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		String sourceFrom = (String) session.getAttribute("sourceFrom");
		if (!sourceFrom.equals("signup")) {
			logger.warn("visitor " + req.getRemoteAddr()
					+ " tried to signup a message without being in the good page, redirected to home");
			resp.sendRedirect("/forum/home");
			return;
		}

		String user = req.getParameter("username");
		String pass = req.getParameter("password");

		if ("".compareTo(user) == 0 || "".compareTo(pass) == 0) {
			logger.warn("visitor " + req.getRemoteAddr() + " tried to sign up with empty fields");
			req.setAttribute("error", "emptyfields");
			req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
		} else if (pass.length() < 8) {
			logger.warn("visitor " + req.getRemoteAddr() + " tried to sign up with a too short password (len:"
					+ pass.length() + ")");
			req.setAttribute("error", "minlength");
			req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
		} else {

			char[] interdit = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]".toCharArray();
			ArrayList<Character> unwantedChars = new ArrayList<>();
			for (char ch : interdit) {
				unwantedChars.add(ch);
			}
			boolean formatOk = true;
			for (char c : user.toCharArray()) {
				if (unwantedChars.contains(c))
					formatOk = false;
			}

			if (!formatOk) {
				logger.warn("visitor " + req.getRemoteAddr() + " tried to sign up with invalid characters: " + user);
				req.setAttribute("error", "invalidChars");
				req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
			}

			else {
				DAOServlet dao;
				try {

					String passSalted = pass + user;
					String hashedSalted = "";

					StringBuffer hexString = sha256(passSalted);
					hashedSalted = hexString.toString();

					dao = DAOServlet.getDAO();

					if ("default".compareTo(user) == 0) { // Invalide username "default"
						logger.warn(
								"visitor " + req.getRemoteAddr() + " tried to sign up with invalid username: " + user);
						req.setAttribute("error", "exist");
						req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
					}

					else if (dao.addUser(user, hashedSalted)) {
						Utilisateur u = dao.getUser(user);
						session.setAttribute("sess", true);
						session.setAttribute("user", user);
						session.setAttribute("utilisateur", u);

						logger.info("The " + u.getRole().getRole() + " has signed up as \"" + user + "\" ip: "
								+ req.getRemoteAddr());
						resp.sendRedirect("/forum/home");
					}

					else {
						logger.warn("visitor " + req.getRemoteAddr()
								+ " tried to sign up with already existing username: " + user);
						req.setAttribute("error", "exist");
						req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
					}
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
						| NoSuchAlgorithmException | SQLException e) {
					logger.error("error while \"" + (String) req.getSession().getAttribute("user")
							+ "\" tried to sign up at ip: " + req.getRemoteAddr() + ", error: " + e.getMessage());
				}
			}
		}
	}

	/**
	 * 
	 * @param passSalted The password to hash
	 * @return The StringBuffer with the password hashed
	 * @throws NoSuchAlgorithmException
	 */
	private StringBuffer sha256(String passSalted) throws NoSuchAlgorithmException {
		MessageDigest digest;
		digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(passSalted.getBytes(StandardCharsets.UTF_8));

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString;
	}

}
