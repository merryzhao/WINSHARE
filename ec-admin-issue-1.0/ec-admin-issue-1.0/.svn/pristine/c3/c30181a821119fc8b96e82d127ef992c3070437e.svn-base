<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 零售包件信息  -->
<div  class="entering_table">
	<table class="entering_table">
		<tr>
			<td colspan="6">零售包件信息</td>
		</tr>
		<tr>
			<td colspan="6"><HR width="100%" SIZE=1>
			</td>
		</tr>
		<!-- 表单布局 -->
		<colgroup>
			<col class="property" />
			<col class="input" />
			<col class="property" />
			<col class="input" />
			<col class="property" />
			<col class="input" />
		</colgroup>
		<!--数据输入 -->
		<tr>
			<td align="right">包件运单号：<label class="red">*</label></td>
			<td>
				<input type="text" id="expressid" name="expressid" tabindex="1" maxlength="32"/>
			</td>
			<td align="right">运单应收包数：</td>
			<td>
				<input type="text" id="shouldquantity" name="shouldquantity" tabindex="5" maxlength="10"/>
			</td>
			<td align="right">发件人电话：</td>
			<td>
				<input type="text" id="phone" name="phone" tabindex="9" maxlength="15"/>
			</td>
		</tr>
		<tr>
			<td align="right">退货签收时间：<label class="red">*</label></td>
			<td>
				<input id="signtime" name="signtime" bind="datetimepicker" style="width:120px" tabindex="2">
			</td>
			<td align="right">运单实收包数：</td>
			<td>
				<input type="text" id="realquantity" name="realquantity" tabindex="6" maxlength="10"/>
			</td>
			<td align="right">发件人地址：</td>
			<td colspan="3">
				<input type="text" id="address" name="address" tabindex="10" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="right">退货签收人：<label class="red">*</label></td>
			<td>
				<input type="text" id="signname" name="signname" value="${creator }" tabindex="3" maxlength="40"/>
			</td>
			<td align="right">暂存库位：</td>
			<td>
				<input type="text" id="storagelocation" name="storagelocation" tabindex="7" maxlength="10"/>
			</td>
			<td align="right">承运商：</td>
			<td>
				<input type="text" id="carrier" name="carrier" tabindex="11" />
			</td>
		</tr>
		<tr>
			<td align="right">退货类型：<label class="red">*</label></td>
			<td>
				<select id="returnType" name="returnType" tabindex="4">
					<c:forEach var="returntypeitem" items="${returntypelist }">
						<option value="${returntypeitem.id }">${returntypeitem.name }</option>
					</c:forEach>
				</select>
			</td>
			<td align="right">发件人：<label class="red">*</label></td>
			<td>
				<input type="text" id="customer" name="customer" tabindex="8" maxlength="40"/>
			</td>
			<td align="right">包件揽收时间：</td>
			<td>
				<input id="expresstime" name="expresstime" bind="datepicker" style="width:120px" tabindex="12"/>
			</td>
		</tr>
		<tr>
			<td align="right" class="property">备注：</td>
			<td colspan="5"><input type="text" id="remark" name="remark" class="long" tabindex="13" maxlength="300"/>
			</td>
		</tr>
	</table>
</div>