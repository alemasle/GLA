<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
	<head>
	<meta charset="utf-8" />
	<title>Profil de "<c:out value="${login}" />"</title>
	
	
	<link rel="stylesheet" href="css/style.css" type="text/css" />
	</head>
	<body class="ltr">
	
	
		<div id="wrapcentre">
	
			<div id="pagecontent">
	
				<table class="tablebg" style="margin-top: 5px;" cellspacing="1"
					cellpadding="0" width="100%">
					<tbody>
						<tr>
							<td class="row1">
								<p class="breadcrumbs">
									<c:choose>
										<c:when test="${ utilisateur.getRole().getRole() != 'Invite' }">
											<a href="/forum/profil?login=<c:out value="${user}"/>"><b><c:out value="${user}" /></b></a>&nbsp;
											<a href="/forum/logout" type=""><b><u>D&eacute;connexion</u></b></a>
										</c:when>
										
										<c:otherwise> Non connect&eacute;&nbsp;
											<a href="/forum/login"><b><u>Connexion</u></b></a>&nbsp;
											<a href="/forum/signup"><b><u>Inscriptions</u></b></a>
										</c:otherwise>
									</c:choose>
								</p>
							</td>
						</tr>
					</tbody>
				</table>
	
				<br clear="all" />	
					<table cellspacing="1" width="100%">
						<tbody>
							
							<tr>
								<td>
									Photo de profil <b><c:out value="${login}"></c:out></b>:<br />
								</td>
								<c:if test="${user == login}">
									<td valign="top" align="right">
										<a href="/forum/uploadavatar"><u>Change your avatar</u></a>
									</td>
								</c:if>
							</tr>
							
							<tr>
								<td>
									<img src="fichiers/${userProfil.getAvatar()}" alt="${login} has no picture" height="10%" width="10%"/>
								</td>
								<td valign="top" align="right">
								 	<a href="/forum/newthread">
										<img src="fichiers/button_topic_new.gif" alt="Post new topic" title="Post new topic" />
									</a>
								</td>
							</tr>

							<tr>
								<td valign="middle" align="left">
									<b>Username: </b> <c:out value="${login}" /> <br />
									<b>Nombre de posts: </b><c:out value="${userProfil.getNbPosts()}" /> <br />
									<b>Inscription: </b><c:out value="${userProfil.getSignUp()}" />
								</td>
							</tr>							
						</tbody>
					</table>
	
				<br clear="all" />
	
					<table class="tablebg" cellspacing="1" width="100%">
						<tbody>	
							<tr>
	
								<th>&nbsp;Topics de <c:out value="${login}" />&nbsp;</th>
								<th>&nbsp;Auteur&nbsp;</th>
								<th>&nbsp;R&eacute;ponses&nbsp;</th>
								<th>&nbsp;Vues&nbsp;</th>
							</tr>

							<c:choose>
								<c:when test="${empty threads_answered}">
									<td>
										<div class="postbody">Cet utilisateur n'a particip&eacute; &agrave; aucun fil de discussion</div>
									</td>
								</c:when>
								
								<c:otherwise>
									<c:forEach items="${threads_answered}" var="thread">
										<tr>
											<td class="row1"><a class="topictitle" href="thread?id=${thread.getId()}" ><c:out value="${thread.getName()}"></c:out></a></td>
											<td class="row2" align="center" width="130">
												<p class="topicauthor">
													<c:choose>
														<c:when test="${user == thread.getAuteur() or (utilisateur.getRole().readProfil())}">
													<a class="username-coloured" href="/forum/profil?login=${thread.getAuteur()}"><c:out value="${thread.getAuteur()}" /></a>
														</c:when>
														<c:otherwise>
															<c:out value="${thread.getAuteur()}" />
														</c:otherwise>
													</c:choose>
												</p>
											</td>
											<td class="row1" align="center" width="50"><p class="topicdetails"><c:out value="${thread.getNbMsg()}" /></p></td>
											<td class="row2" align="center" width="50"><p class="topicdetails"><c:out value="${thread.getNbVues()}" /></p></td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>

						</tbody>
					</table>
					<br clear="all" />
			</div>
	
			<table class="tablebg" style="margin-top: 5px;" cellspacing="1"
				cellpadding="0" width="100%">
				<tbody>
					<tr>
						<td class="row1">
							<p class="breadcrumbs"><a href="/forum/home"><u><b>Index du forum</b></u></a></p>
						</td>
					</tr>
				</tbody>
			</table>
	
		</div>
	
	</body>
</html>
