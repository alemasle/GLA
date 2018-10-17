<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
	<head>
	
	<title>FORUM</title>
	
	
	<link rel="stylesheet" href="fichiers/style.css" type="text/css" />
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
											<a href="/forum/profil?login=${user}"><b><c:out value="${user}" /></b></a>&nbsp;
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
								<td valign="middle" align="left">
								
									<c:if test="${sess}">
										<a href="/forum/newthread">
											<img src="fichiers/button_topic_new.gif" alt="Post new topic" title="Post new topic" />
										</a>
									</c:if>						
								
								</td>
							</tr>
						</tbody>
					</table>
	
				<br clear="all" />
	
					<table class="tablebg" cellspacing="1" width="100%">
						<tbody>
							<tr>
								<td class="cat" colspan="4">
									<table cellspacing="0" width="100%">
										<tbody>
											<tr class="nav">
												<td valign="middle">&nbsp;</td>
												<td valign="middle" align="right">&nbsp;</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
	
							<tr>
	
								<th>&nbsp;Topics&nbsp;</th>
								<th>&nbsp;Auteur&nbsp;</th>
								<th>&nbsp;R&eacute;ponses&nbsp;</th>
								<th>&nbsp;Vues&nbsp;</th>
							</tr>

							<c:forEach items="${threads}" var="thread">
								<tr>
									<td class="row1">
										<c:choose>
											<c:when test="${utilisateur.getRole().readThread()}">
												<a class="topictitle" href="thread?id=${thread.getId()}" ><c:out value="${thread.getName()}" /></a>
											</c:when>
											<c:otherwise>
												<c:out value="${thread.getName()}" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="row2" align="center" width="130">
										<p class="topicauthor">
											<c:choose>
												<c:when	test="${user == thread.getAuteur() or (utilisateur.getRole().readProfil())}">
													<a class="username-coloured"
														href="/forum/profil?login=${thread.getAuteur()}"><c:out
															value="${thread.getAuteur()}" /></a>
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
