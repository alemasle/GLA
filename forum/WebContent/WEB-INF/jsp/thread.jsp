<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
	<head>
	<meta charset="utf-8" />
	<title><c:out value="${threadName}" /></title>
	
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
											<a href="/forum/profil?login=<c:out value="${user}" />"><b><c:out value="${user}" /></b></a>&nbsp;
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
							<c:if test="${sess}">
								<td valign="middle" align="left" colspan="4" nowrap="nowrap">
									<a href="/forum/newthread"><img src="fichiers/button_topic_new.gif" alt="Post new topic" title="Post new topic" /></a>&nbsp;
									<a href="/forum/newpost"><img src="fichiers/button_topic_reply.gif" alt="Reply to topic" title="Reply to topic" /></a>
								</td>
							</c:if>
						</tr>
					</tbody>
				</table>

				<br clear="all" />
	
				<table class="tablebg" cellspacing="1" width="100%">
				<c:choose>
					<c:when test="${empty messages}">
						<td>
							<div class="postbody">Aucun messages dans ce fil de discussion</div>
						</td>
					</c:when>
					
				<c:otherwise>
					<c:forEach items="${messages}" var="message">
							<tbody>
								<tr class="row2">
									<td valign="middle" align="center"><b class="postauthor"><a class="username-coloured" href="/forum/profil?login=${message.getAuteur().getLogin()}"><c:out value="${message.getAuteur().getLogin()}" /> </a></b></td>
									<td width="100%" height="25">
										<table cellspacing="0" width="100%">
											<tbody>
												<tr>
													<td class="gensmall" width="100%">
														<div style="float: left;">&nbsp;
															<b><c:out value="${message.getThreadName()}" />:</b>
														</div>
														<div style="float: right;">
														<c:if test="${message.getEdited()}">
															<span class="postdetails"> <b>(Edited)</b></span>
														</c:if>
															<b>Posted:</b> <c:out value="${message.getDate()}" />&nbsp;
														</div>
													</td>
												</tr>								
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
							
							<tr class="row2">
			
								<td class="profile" valign="top">
									<table cellspacing="4" align="center" width="100">
										<tr>
											<td align="left">
												<img src="fichiers/<c:out value="${message.getAuteur().getAvatar()}" />" alt="<c:out value="${login} has no picture" />" height="55%" width="55%"/>
											</td>
										</tr>
										<tr>
											<td class="postdetails"> <b>Posts:&nbsp;</b><c:out value="${message.getAuteur().getNbPosts()}" /></td>
										</tr>

										<c:if test="${user == message.getAuteur().getLogin() and (message.getAuteur().getRole().editMessage()) or (utilisateur.getRole().editAllMessages()) }">
											<tr>
												<td valign="bottom">
													<a href="/forum/editpost?id=<c:out value="${message.getId()}" />">
														<b><u>Editer</u></b>
													</a>
												</td>
											</tr>
										</c:if>
										<c:if test="${user == message.getAuteur().getLogin() and (message.getAuteur().getRole().deleteMessage()) or (utilisateur.getRole().deleteAllMessages()) }">
											<tr>
												<td valign="top">
													<a href="/forum/deletemessage?id=<c:out value="${message.getId()}" />">
														<b><u>Supprimer</u></b>
													</a>
												</td>
											</tr>
										</c:if>
									</table> 
								</td>
								
								<td valign="top">
									<table cellspacing="5" width="100%">
										<tbody>
											<tr>
												<td>
													<div class="postbody" style="word-break:break-all;"><c:out value="${message.getTexte()}" /></div>
													<br clear="all" /><br />
													<table cellspacing="0" width="100%">
														<tbody>
															<tr valign="middle">
																<td class="gensmall" align="right"></td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</c:forEach>
					
					</c:otherwise>
				
					</c:choose>
					<tr>
						<td class="spacer" colspan="2" height="1"><img src="fichiers/spacer.gif" alt="" width="1" height="1" /></td>
					</tr>
				</table>
	
	
			</div>
		
			<br clear="all" />
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
