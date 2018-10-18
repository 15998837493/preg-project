<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>宫高腹围对照</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
$().ready(function(){
	var lmp = ${custlmp};
	$("td[lmp='"+lmp+"']").parent().addClass("success");
});
</script>

<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-heading">
					<h4 class="timeline-title">宫高腹围对照</h4>
				</div>
				<div class="timeline-body form-horizontal">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center">宫高腹围监测</div>
						<div class="panel-body" style="padding: 0px;">
						    <div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr class="active">
											<th class='text-center'></th>
											<th class='text-center' >妊娠周数</th>
									     	<th class='text-center'>下限(厘米)</th>
									     	<th class='text-center'>上限(厘米)</th>
									     	<th class='text-center'>推荐(厘米)</th>
									   </tr>
									</thead>
									<tbody>
									<!-- 宫高部分-->
										<tr>
											<td class='text-center'>
														<strong>宫高</strong>							
											</td>
											<td class='text-center' lmp="20">20</td>
									     	<td class='text-center'>15.3</td>
									     	<td class='text-center'>21.4</td>
									     	<td class='text-center'>18.6</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="21">21</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>19</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="22">22</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>20.2</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="23">23</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>21.1</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="24">24</td>
									     	<td class='text-center'>22</td>
									     	<td class='text-center'>25.1</td>
									     	<td class='text-center'><font color="red">22</font>-24</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="25">25</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>23.4</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="26">26</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>23.9</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="27">27</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>24.8</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="28">28</td>
									     	<td class='text-center'>22.4</td>
									     	<td class='text-center'>29</td>
									     	<td class='text-center'><font color="red">25.6</font>-26</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="29">29</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>26.5</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="30">30</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>27.8</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="31">31</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>28.6</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="32">32</td>
									     	<td class='text-center'>25.3</td>
									     	<td class='text-center'>32</td>
									     	<td class='text-center'><font color="red">29</font></td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="33">33</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>29.8</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="34">34</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>30.6</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="35">35</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>31.1</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="36">36</td>
									     	<td class='text-center'>29.8</td>
									     	<td class='text-center'>34.5</td>
									     	<td class='text-center'><font color="red">31.6</font>-32</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="37">37</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>31.9</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="38">38</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>32.3</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="39">39</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>32.8</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="40">40</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'></td>
									     	<td class='text-center'><font color="red">33</font></td>
										</tr>
										<!-- 腹围部分-->
										<tr>
											<td class='text-center'>
												<strong>腹围</strong>
											</td>
											<td class='text-center' lmp="20">20</td>
									     	<td class='text-center'>76</td>
									     	<td class='text-center'>89</td>
									     	<td class='text-center'>82</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="24">24</td>
									     	<td class='text-center'>80</td>
									     	<td class='text-center'>91</td>
									     	<td class='text-center'>85</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="28">28</td>
									     	<td class='text-center'>82</td>
									     	<td class='text-center'>94</td>
									     	<td class='text-center'>87</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="32">32</td>
									     	<td class='text-center'>84</td>
									     	<td class='text-center'>95</td>
									     	<td class='text-center'>89</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="36">36</td>
									     	<td class='text-center'>86</td>
									     	<td class='text-center'>98</td>
									     	<td class='text-center'>92</td>
										</tr>
										<tr>
											<td class='text-center'>
											</td>
											<td class='text-center' lmp="40">40</td>
									     	<td class='text-center'>89</td>
									     	<td class='text-center'>100</td>
									     	<td class='text-center'>94</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>
</body>
</html>

<!-- <div class="container-fluid"> -->
<!-- <div class="row"> -->
<!-- <div class="col-xs-12"> -->
<!-- <div class="panel panel-danger"> -->
<!--     <div class="panel-heading text-center"><h3>宫高腹围监测</h3></div> -->
<!--     <div class="table-responsive"> -->
<!-- 						<table class="table table-bordered table-hover"> -->
<!-- 							<thead> -->
<!-- 								<tr class="active"> -->
<!-- 									<th class='text-center'></th> -->
<!-- 									<th class='text-center' >妊娠周数</th> -->
<!-- 							     	<th class='text-center'>下限(厘米)</th> -->
<!-- 							     	<th class='text-center'>上限(厘米)</th> -->
<!-- 							     	<th class='text-center'>推荐(厘米)</th> -->
<!-- 							   </tr> -->
<!-- 							</thead> -->
<!-- 							<tbody> -->
							<!-- 宫高部分-->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 												<strong>宫高</strong>							 -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="20">20</td> -->
<!-- 							     	<td class='text-center'>15.3</td> -->
<!-- 							     	<td class='text-center'>21.4</td> -->
<!-- 							     	<td class='text-center'>18.6</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="21">21</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>19</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="22">22</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>20.2</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="23">23</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>21.1</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="24">24</td> -->
<!-- 							     	<td class='text-center'>22</td> -->
<!-- 							     	<td class='text-center'>25.1</td> -->
<!-- 							     	<td class='text-center'><font color="red">22</font>-24</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="25">25</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>23.4</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="26">26</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>23.9</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="27">27</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>24.8</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="28">28</td> -->
<!-- 							     	<td class='text-center'>22.4</td> -->
<!-- 							     	<td class='text-center'>29</td> -->
<!-- 							     	<td class='text-center'><font color="red">25.6</font>-26</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="29">29</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>26.5</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="30">30</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>27.8</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="31">31</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>28.6</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="32">32</td> -->
<!-- 							     	<td class='text-center'>25.3</td> -->
<!-- 							     	<td class='text-center'>32</td> -->
<!-- 							     	<td class='text-center'><font color="red">29</font></td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="33">33</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>29.8</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="34">34</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>30.6</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="35">35</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>31.1</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="36">36</td> -->
<!-- 							     	<td class='text-center'>29.8</td> -->
<!-- 							     	<td class='text-center'>34.5</td> -->
<!-- 							     	<td class='text-center'><font color="red">31.6</font>-32</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="37">37</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>31.9</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="38">38</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>32.3</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="39">39</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'>32.8</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="40">40</td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'></td> -->
<!-- 							     	<td class='text-center'><font color="red">33</font></td> -->
<!-- 								</tr> -->
								<!-- 腹围部分-->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 										<strong>腹围</strong> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="20">20</td> -->
<!-- 							     	<td class='text-center'>76</td> -->
<!-- 							     	<td class='text-center'>89</td> -->
<!-- 							     	<td class='text-center'>82</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="24">24</td> -->
<!-- 							     	<td class='text-center'>80</td> -->
<!-- 							     	<td class='text-center'>91</td> -->
<!-- 							     	<td class='text-center'>85</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="28">28</td> -->
<!-- 							     	<td class='text-center'>82</td> -->
<!-- 							     	<td class='text-center'>94</td> -->
<!-- 							     	<td class='text-center'>87</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="32">32</td> -->
<!-- 							     	<td class='text-center'>84</td> -->
<!-- 							     	<td class='text-center'>95</td> -->
<!-- 							     	<td class='text-center'>89</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="36">36</td> -->
<!-- 							     	<td class='text-center'>86</td> -->
<!-- 							     	<td class='text-center'>98</td> -->
<!-- 							     	<td class='text-center'>92</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td class='text-center'> -->
<!-- 									</td> -->
<!-- 									<td class='text-center' lmp="40">40</td> -->
<!-- 							     	<td class='text-center'>89</td> -->
<!-- 							     	<td class='text-center'>100</td> -->
<!-- 							     	<td class='text-center'>94</td> -->
<!-- 								</tr> -->
<!-- 							</tbody> -->
<!-- 						</table> -->
<!-- 					</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->