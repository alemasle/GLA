<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
	<head>
	
	<title>THREAD</title>
	
	
	
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
									<c:when test="${ sess }"> <b><c:out value="${ user }" /></b>
										<a href="/forum/logout" type=""> <b><u>D&eacute;connexion</u></b></a>
									</c:when>
									
									<c:otherwise> Non connect&eacute; 
										<a href="/forum/login"> <b><u>Connexion</u></b></a>
										<a href="/forum/signup"> <b><u>Inscriptions</u></b></a>
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
										<td valign="middle" align="center"><b class="postauthor"> <c:out value="${message.getMessage().getAuteur()}" /></b></td>
										<td width="100%" height="25">
											<table cellspacing="0" width="100%">
												<tbody>
													<tr>
														<td class="gensmall" width="100%">
															<div style="float: left;">&nbsp;
																<b><c:out value="${message.getMessage().getThreadName()}" />:</b>
															</div>
															<div style="float: right;">
																<b>Posted:</b> <c:out value="${message.getMessage().getDate()}" />&nbsp;
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
										<table cellspacing="4" align="center" width="150">
				
										</table> <span class="postdetails"> <b>Posts:</b> <c:out value="${message.getNbPosts()}" />
									</span>
				
									</td>
									<td valign="top">
										<table cellspacing="5" width="100%">
											<tbody>
												<tr>
													<td>
														<div class="postbody"><c:out value="${message.getMessage().getTexte()}" /></div>
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
