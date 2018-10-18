<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>胎龄计算器</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
$().ready(function(){
	var lmp = ${custlmp};
	$("td[lmp='"+lmp+"']").parent().addClass("success");
});
</script>
<body>
<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-heading">
					<h4 class="timeline-title">胎龄计算器</h4>
				</div>
				<div class="timeline-body form-horizontal">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center">胎儿监测(B超值)</div>
						<div class="panel-body" style="padding: 0px;">
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr class="active">
											<th class='text-center'>周数</th>
											<th class='text-center' >胎儿体重单位(g)</th>
									     	<th class='text-center'>胎儿股骨长(mm)</th>
									     	<th class='text-center'>胎儿双顶径(mm)</th>
									     	<th class='text-center'>胎儿腹围(mm)</th>
									   </tr>
									</thead>
									<tbody>
										<tr>
											<td class='text-center' lmp="17">17</td>
											<td class='text-center' ></td>
									     	<td class='text-center'>23</td>
									     	<td class='text-center'>36</td>
									     	<td class='text-center'>112</td>
										</tr>
										<tr>
											<td class='text-center' lmp="18">18</td>
											<td class='text-center'></td>
									     	<td class='text-center'>26</td>
									     	<td class='text-center'>39</td>
									     	<td class='text-center'>124</td>
										</tr>
										<tr>
											<td class='text-center' lmp="19">19</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>29</td>
									     	<td class='text-center'>43</td>
									     	<td class='text-center'>135</td>
										</tr>
										<tr>
											<td class='text-center' lmp="20">20</td>
									     	<td class='text-center'></td>
									     	<td class='text-center'>32</td>
									     	<td class='text-center'>46</td>
									     	<td class='text-center'>147</td>
										</tr>
										<tr>
											<td class='text-center' lmp="21">21</td>
									     	<td class='text-center'>320</td>
									     	<td class='text-center'>35</td>
									     	<td class='text-center'>50</td>
									     	<td class='text-center'>159</td>
										</tr>
										<tr>
											<td class='text-center' lmp="22">22</td>
									     	<td class='text-center'>320</td>
									     	<td class='text-center'>37</td>
									     	<td class='text-center'>53</td>
									     	<td class='text-center'>170</td>
										</tr>
										<tr>
											<td class='text-center' lmp="23">23</td>
									     	<td class='text-center'>365</td>
									     	<td class='text-center'>40</td>
									     	<td class='text-center'>56</td>
									     	<td class='text-center'>182</td>
										</tr>
										<tr>
											<td class='text-center' lmp="24">24</td>
									     	<td class='text-center'>417</td>
									     	<td class='text-center'>43</td>
									     	<td class='text-center'>59</td>
									     	<td class='text-center'>193</td>
										</tr>
										<tr>
											<td class='text-center' lmp="25">25</td>
									     	<td class='text-center'>477</td>
									     	<td class='text-center'>45</td>
									     	<td class='text-center'>62</td>
									     	<td class='text-center'>204</td>
										</tr>
										<tr>
											<td class='text-center' lmp="26">26</td>
									     	<td class='text-center'>546</td>
									     	<td class='text-center'>48</td>
									     	<td class='text-center'>64</td>
									     	<td class='text-center'>215</td>
										</tr>
										<tr>
											<td class='text-center' lmp="27">27</td>
									     	<td class='text-center'>627</td>
									     	<td class='text-center'>50</td>
									     	<td class='text-center'>67</td>
									     	<td class='text-center'>226</td>
										</tr>
										<tr>
											<td class='text-center' lmp="28">28</td>
									     	<td class='text-center'>720</td>
									     	<td class='text-center'>53</td>
									     	<td class='text-center'>70</td>
									     	<td class='text-center'>237</td>
										</tr>
										<tr>
											<td class='text-center' lmp="29">29</td>
									     	<td class='text-center'>829</td>
									     	<td class='text-center'>55</td>
									     	<td class='text-center'>72</td>
									     	<td class='text-center'>248</td>
										</tr>
										<tr>
											<td class='text-center' lmp="30">30</td>
									     	<td class='text-center'>955</td>
									     	<td class='text-center'>57</td>
									     	<td class='text-center'>75</td>
									     	<td class='text-center'>258</td>
										</tr>
										<tr>
											<td class='text-center' lmp="31">31</td>
									     	<td class='text-center'>1100</td>
									     	<td class='text-center'>60</td>
									     	<td class='text-center'>77</td>
									     	<td class='text-center'>269</td>
										</tr>
										<tr>
											<td class='text-center' lmp="32">32</td>
									     	<td class='text-center'>1284</td>
									     	<td class='text-center'>62</td>
									     	<td class='text-center'>80</td>
									     	<td class='text-center'>279</td>
										</tr>
										<tr>
											<td class='text-center' lmp="33">33</td>
									     	<td class='text-center'>1499</td>
									     	<td class='text-center'>64</td>
									     	<td class='text-center'>82</td>
									     	<td class='text-center'>290</td>
										</tr>
										<tr>
											<td class='text-center' lmp="34">34</td>
									     	<td class='text-center'>1728</td>
									     	<td class='text-center'>66</td>
									     	<td class='text-center'>85</td>
									     	<td class='text-center'>300</td>
										</tr>
										<tr>
											<td class='text-center' lmp="35">35</td>
									     	<td class='text-center'>1974</td>
									     	<td class='text-center'>68</td>
									     	<td class='text-center'>87</td>
									     	<td class='text-center'>311</td>
										</tr>
										<tr>
											<td class='text-center' lmp="36">36</td>
									     	<td class='text-center'>2224</td>
									     	<td class='text-center'>70</td>
									     	<td class='text-center'>89</td>
									     	<td class='text-center'>321</td>
										</tr>
										<tr>
											<td class='text-center' lmp="37">37</td>
									     	<td class='text-center'>2455</td>
									     	<td class='text-center'>72</td>
									     	<td class='text-center'>91</td>
									     	<td class='text-center'>331</td>
										</tr>
										<tr>
											<td class='text-center' lmp="38">38</td>
									     	<td class='text-center'>2642</td>
									     	<td class='text-center'>74</td>
									     	<td class='text-center'>93</td>
									     	<td class='text-center'>341</td>
										</tr>
										<tr>
											<td class='text-center' lmp="39">39</td>
									     	<td class='text-center'>2790</td>
									     	<td class='text-center'>76</td>
									     	<td class='text-center'>96</td>
									     	<td class='text-center'>351</td>
										</tr>
										<tr>
											<td class='text-center' lmp="40">40</td>
									     	<td class='text-center'>2891</td>
									     	<td class='text-center'>78</td>
									     	<td class='text-center'>98</td>
									     	<td class='text-center'>361</td>
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