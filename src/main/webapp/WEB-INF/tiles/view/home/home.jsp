<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  

<div class="row">
    <div class="col-md-12">
		<h4 >Reconcile</h4>
	
	    <!--  <c:forEach var="i" begin="1" end="50">
	    <p>${i}:<c:forEach var="j" begin="1" end="${i + 50}"> Test</c:forEach></p>
	    </c:forEach> -->
	    
	    
	    <form:form class="form-horizontal" method="post"
                modelAttribute="cusipRequest" action="/reconcileCuspid">
                
                 <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="cusipId">Enter cusip-Id:</form:label>
		         
		          <div class="col-sm-2">
		            <form:input class="form-control" path="cusipId"/>
		            <form:errors path="cusipId" />
		            
		            </div>
		            </div>
		            <div class="form-group">
		            <div class="col-sm-4">
		            <button type="submit" class="btn btn-primary  pull-right">Validate</button>
		            </div>
		            </div>
                   
               
				
				<c:choose>
				<c:when test="${not empty cusipRequest.results}">
				
				 <div class="row">
					<div class="col-sm-3 text-center"> <font color="blue"><strong>CUSIP ID :  </strong>${cusipRequest.cusipId} </font></div>
					<br></br>
				</div>
				
				<div class="container">
				
				  <table class="table">
						 <thead class="thead-inverse">						
							<tr>
								<th>Attribute Name</th>
								<th>Html Value</th>
								<th>CSV Value</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="rec" items="${cusipRequest.results}">  
						     <tr  >
								<td  class="table-active">${rec.attributeName}</td>
								<td class="table-active">${rec.htmlValue}</td>
								<td class="table-active">${rec.csvValue }</td>
								<c:if test = "${rec.matchValue == 'MATCH'}">
								  <td class="bg-success"><font color="green">${rec.matchValue}</font></td>
								</c:if>
								
								
								<c:if test = "${rec.matchValue == 'NOMATCH'}">
								<td class="bg-danger">${rec.matchValue}</td>
								</c:if>
								
								<c:if test = "${rec.matchValue == 'KeyNotFound'}">
								<td class="bg-danger"> ${rec.matchValue}</td>
								</c:if>
								
								
								
							</tr>
							</c:forEach>
						
						
						</tbody>
				</table>
				  
				</div>
				</c:when>
				<c:otherwise></c:otherwise>
                
                </c:choose>

	    </form:form>
    </div>
</div>