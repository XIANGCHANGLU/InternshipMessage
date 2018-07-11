<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page session ="false" %>
<div data-role="pieCtn" style="width:800px;height:500px;margin-left:200px"></div>
<script src="resource/js/echarts-all-2.2.7.min.js"></script>
<script src="resource/js/jquery.min.js?v=2.1.4"></script>
<script type="text/javascript">
var $testPieCtn=$('[data-role=pieCtn]'),  //饼状图的jQuery对象
refreshTimeHandler,
options = {
    title : {
        text: '学生实习分数图表',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['优秀（85-100）','良好（70-85）','及格（60-75）','不及格（小于60）']
    },
    //工具栏
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {
                show: true,
                type: ['pie', 'funnel'],
                option: {
                    funnel: {
                        x: '15%',
                        width: '70%',
                        funnelAlign: 'center',
                        max: 335
                    }
                }
            },
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : false,//是否可以拖拽
    series : [
        {
            name:'比例（单位：个）',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            itemStyle:{ 
                normal:{ 
                    label:{ 
                       show: true, 
                       formatter: '{b} : {c} ({d}%)' 
                    }, 
                    labelLine :{show:true}
                } 
            } ,
            data:[
                {value:'<%=request.getAttribute("Excellent")%>', name:'优秀（85-100）'},
                {value:'<%=request.getAttribute("good")%>', name:'良好（70-85）'},
                {value:'<%=request.getAttribute("pass")%>', name:'及格（60-75）'},
                {value:'<%=request.getAttribute("NoPass")%>', name:'不及格（小于60）'},
            ]
        }
    ]
};
/*数据加载*/
	//定义高度、宽度

	//初始化饼状图的echarts对象,并设置主题
	testPieChart=echarts.init($testPieCtn[0],echarts.config.skin.MACARONS);
	testPieChart.setOption(options);
</script>