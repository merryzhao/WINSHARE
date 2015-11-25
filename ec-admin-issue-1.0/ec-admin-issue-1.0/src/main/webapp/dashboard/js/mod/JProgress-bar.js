/**
 * @author jiangsk540
 * Download by http://www.codefans.net
 */
define(function(require) {
    var DOM,RES,JPROGRESSBARS,JPROGRESSBARS_TIMERS,JPROGRESSBARS_DOC_FRAGMENT,RE_PCT,F_NOOP,ISIE,EASING_EFFECT;
    var $=require("jQuery"),
    	AppEvent=require("../event/app");
		View=require("../view/summary");
    DOM     =   document;
    ISIE    =   navigator.userAgent.toLowerCase().indexOf("msie")!=-1;
    
    RES     =   {
        //JProgressBar 的样式
        CSS     :   '.j-progress-bar-box{display:none;border:1px solid gray; background:#CFD0D2;  opacity:0.6;border-radius:3px; -moz-border-radius:3px;-khtml-border-radius:3px;-webkit-border-radius:3px; overflow:hidden; position:relative; margin:0 auto;}'
                    +'.j-progress-bar-box .block{ z-index:1; background:#8c1d23; position:absolute; left:0px; overflow:hidden;}'
                    +'.j-progress-bar-box .block{font:0/0 airal; }'
                    +'.j-progress-bar-box .highlight{font:0/0 airal;}'
                    +'.j-progress-bar-box .text{z-index:3;width:100%;height:100%; font-family:"宋体",airal; font-size:12px; display:table;text-align:center;position:absolute; left:0px; }'
                    +(ISIE?'.j-progress-bar-box .highlight{ z-index:2; position:absolute; left:0px; top:0px;overflow:hidden;  background:#fff; filter:alpha(opacity=60);}'
                    +'.j-progress-bar-box .text .content{vertical-align:middle; text-align:center;display:table-cell; width:100%; *position:absolute; *top:50%; *left:0px;}'
                    +'.j-progress-bar-box .text .content div{display:block;*position:relative; *top:-50%;}'
                    :'.j-progress-bar-box .highlight{ z-index:2; position:absolute; left:0px; top:0px;overflow:hidden;  background:#fff; opacity:0.6;}'
                    +'.j-progress-bar-box .text .content{vertical-align:middle; text-align:center;display:table-cell; width:100%;line-height:24px;}'
                    +'.j-progress-bar-box .text .content div{display:block;}')
                    +'.j-progress-bar-box .text-mask{z-index:4;position:absolute; left:0px; overflow:hidden;}'
                    +'.j-progress-bar-box .text-mask .text{color:#FFF;}'
                    +'.j-progress-bar-box-v{ height:200px; width:20px;}'
                    +'.j-progress-bar-box-v .highlight{width:50%; height:100%;}'
                    +'.j-progress-bar-box-v .block{width:100%; height:0%; bottom:0px;}'
                    +'.j-progress-bar-box-v .text-mask{  width:100%; height:0%; bottom:0px;}'
                    +'.j-progress-bar-box-v .text-mask .text{width:20px;  height:200px;bottom:0px; }'
                    +'.j-progress-bar-box-l{ height:20px; width:200px;}'
                    +'.j-progress-bar-box-l .highlight{width:100%; height:50%;}'
                    +'.j-progress-bar-box-l .block{width:0%; height:100%; left:0px;}'
                    +'.j-progress-bar-box-l .text-mask{  width:0%; height:100%; left:0px;}'
                    +'.j-progress-bar-box-l .text-mask .text{left:0px; width:200px; height:20px;}',
        //JProgressBar 的HTML模板
        HTMLTPL :  '<div class="j-progress-bar-box j-progress-bar-box-{#direction} {#cls}" id="{#id}" style="{#size}">'
                   // +'  <div class="highlight"></div>'
                    +'  <div class="block" id="{#id}-block"></div>'
                    +'  <div class="text-mask" id="{#id}-text-mask">'
                    +'      <div class="text"  style="{#size}"><div class="content"><div id="{#id}-text1"></div></div></div>'
                    +'  </div>'
                    +'  <div class="text" ><div class="content"><div id="{#id}-text2"></div></div></div>'
                    +'</div>'
    };
    //匹配百分比格式的字符串
    RE_PCT  =   /^(?!%)\d*(?:\.\d+)?%$/;
    
    F_NOOP  =   function(){};
    JPROGRESSBARS_DOC_FRAGMENT = DOM.createDocumentFragment().appendChild(DOM.createElement("DIV"));
    //使用的缓动效果算法
    EASING_EFFECT   =  function(t,b,c,d){
        if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
            return -c/2 * ((t-=2)*t*t*t - 2) + b;
    }
    /**
     * JProgressBar类
     * 创建一个进度条
     * @param {Object} config 配置
     *  config:{
     *       id             : {String},     进度条的ID，默认会自动生成一个ID值
     *       direction      : {String},     进度条的方向，值为v时进度条的方向是垂直的，值为l时进度条方向是水平的，默认是值是l
     *       width          : {Number},     进度条的宽度
     *       height         : {Number},     进度条的高度
     *       maxValue       : {Number},     进度条的最大值
     *       value          : {Number},     进度条的初始值
     *       text           : {String},     进度条中显示的文字字符串，字符串中出现的{#maxValue},{#value},{#pct} 分别表示，同时也会被替换为 进度条的最大值，当前进度值，当前进度值的百分比值        
     *       cls            : {String},     为进度条自定义样式类
     *          样式定义说明
     *          .样式类                                                         进度条最外层框
     *          .样式类 .block               进度条块
     *          .样式类 .highlight           高亮显示的块
     *          .样式类 .text                文本
     *          .样式类 .text-mask .text     遮照文本  
     *       renderTo       : {Element},    进度条所在父元素,如果未指定则会使用document.write 来创建
     *       beforeRender   : {Function},   onBeforeRender事件    
     *       render         : {Function},   onRender事件        
     *       beforeDestroy  : {Function},   onBeforeDestroy事件   
     *       destroy        : {Function},   onDestroy事件 
     *       progressChange : {Function},   onProgressChange事件  
     *       progressed     : {Function},   onProgressed事件 
     *       complete       : {Function}    onComplete事件 
     *  } 
     */
    JProgressBar = function(config){
        var id,direction,htmlTpl,value;
        config                  =   config||{};
        this.id                 =   id = config.id          ||  "JProgressBar-"+new Date().getTime()+~~(Math.random()*100000); 
        this.direction          =   direction               =   config.direction||"l";  
        this.width              =   ~~config.width          ||  (direction=="l"?200:20);
        this.height             =   ~~config.height         ||  (direction=="l"?20:200);
        this.maxValue           =   ~~config.maxValue       ||  (direction=="l"?this.width:this.height);
        this.value              =   0;
        this.pct                =   "0%";
        this.text               =   config.text===undefined?"{#pct}":config.text;
        this.cls                =   config.cls===undefined?"":config.cls;
        this.changeProp         =   this.direction=="v"?"height":"width";
        config.render           &&  (this.onRender          =   config.render);
        config.beforeRender     &&  (this.onBeforeRender    =   config.beforeRender);
        config.beforeDestroy    &&  (this.onBeforeDestroy   =   config.beforeDestroy);
        config.destroy          &&  (this.onDestroy         =   config.destroy);
        config.progressChange   &&  (this.onProgressChange  =   config.progressChange);
        config.progressed       &&  (this.onProgressed      =   config.progressed);
        config.complete         &&  (this.onComplete        =   config.complete);
        value                   =   config.value||0;
        this.onBeforeRender();
        JProgressBar.create({id:id,direction:direction,cls:this.cls,size:"width:"+this.width+"px;height:"+this.height+"px;"},config.renderTo);
        this.box                =   DOM.getElementById(id);
        this.parent             =   this.box.parentNode;
        this.block              =   DOM.getElementById(id+"-block");
        this.textMask           =   DOM.getElementById(id+"-text-mask");
        this.text1              =   DOM.getElementById(id+"-text1");
        this.text2              =   DOM.getElementById(id+"-text2");  
        this.box.style.display  =   "block";
        JPROGRESSBARS[id]       =   this;
        this.onRender();
        if(value){
            this.setValue(value);
        };
		
    }
    //被创建进度条实例引用的集合
    JPROGRESSBARS           =   {};
    
    JPROGRESSBARS_TIMERS    =   {};
    
    //事件
    /**
     * JProgressBar.prototype.onBeforeRender 触发在进度条被渲染之前
     */
    JProgressBar.prototype.onBeforeRender   =   F_NOOP;
    /**
     * JProgressBar.prototype.onRender 触发在进度条被渲染完成后
     */
    JProgressBar.prototype.onRender         =   F_NOOP;
    /**
     * JProgressBar.prototype.onBeforeDestroy 触发在进度条被销毁之前
     */
    JProgressBar.prototype.onBeforeDestroy  =   F_NOOP;
    
    /**
     * JProgressBar.prototype.onDestroy 触发在进度条被销毁后
     */
    JProgressBar.prototype.onDestroy        =   F_NOOP;
    /**
     * JProgressBar.prototype.onProgressChange 触发在进度条进度被改变后
     */
    JProgressBar.prototype.onProgressChange =   F_NOOP;
    /**
     * JProgressBar.prototype.onProgressed 触发在进度条 进度移动到某个进度完成之后
     */
    JProgressBar.prototype.onProgressed     =   F_NOOP;
    /**
     * JProgressBar.prototype.onComplete 触发在进度条进度达到100%时
     */
    JProgressBar.prototype.onComplete       =   F_NOOP;
    
    
    JProgressBar.prototype.start =   function(){
		console.info("method invalid");
		return;
    		var model=this;
    		$.ajax({
				method:"get",
				url:"http://console.winxuan.com/dashboard/summary?format=json&start=2012-11-11%2000:00:00",
				success:function(data){
					model.calculate(data.orderStatistics);
				},
				error:function(xhr){
					console.info(xhr.state);
				},
				complete:function(){
					clearTimeout(this.pollerTimer);
					this.pollTimer=setTimeout(function(){model.start()},10000);
				},
				dataType:"json"

			});
    }
    JProgressBar.prototype.calculate =   function(data){
		this.setValue(data.rate+"%",false);
		this.setText(data.text);
    }
    /**
     * JProgressBar.prototype.setText 设置当前进度条实例中显示的文本
     * @param   {String} text         进度条中显示的文字字符串，字符串中出现的{#maxValue},{#value},{#pct} 分别表示，同时也会被替换为 进度条的最大值，当前进度值，当前进度值的百分比值
     * @return  {Object}              返回当前进度条实例对象
     */
    JProgressBar.prototype.setText  =   function(text){
        text  =   JProgressBar.tpl(text||this.text,this);
        this.text1.innerHTML =   this.text2.innerHTML =  this.direction=="v"?text.split("#").join("<br/>"):text;
        return this;
    }
    /**
     * JProgressBar.prototype.setValue      设置当前进度条实例中显示的文本
     * @param   {Number/Float}    value     进度条的值，接受类似于 100,'0.23%',253.23,'235%','.5%' 的值 
     * @param   {Boolean}         animation 指定改变进度条进度值是。是否采用动画效果，默认为true
     * @return  {Object}                    返回当前进度条实例对象
     */
    JProgressBar.prototype.setValue =   function(value,animation){
       var sign,b,c,d,t,timer,block,textMask,p,text,oldValue,maxValue,jpb,events;
        jpb         =   this;
        jpb.value   =   RE_PCT.test(jpb.value)?~~(parseFloat(jpb.value)/100*jpb.maxValue):jpb.value;
        value       =   RE_PCT.test(value)?~~(parseFloat(value)/100*jpb.maxValue):value;
        jpb.value   =   Math.max(Math.min(jpb.value,jpb.maxValue),0);
        
        value       =   Math.max(Math.min(value,jpb.maxValue),0);
        animation   =   animation===undefined?true:animation; 
        text        =   jpb.text;
        p           =   jpb.changeProp;
        block       =   jpb.block.style;
        textMask    =   jpb.textMask.style;
        events      =   [];
        clearInterval(JPROGRESSBARS_TIMERS[jpb.id]);
        if(animation){
            oldValue=   jpb.value;
            maxValue=   jpb.maxValue;
            sign    =   value>=oldValue?1:-1;
            b       =   0;
            c       =   Math.abs(value-oldValue);
            d       =   60;
            t       =   0;
            timer   =   JPROGRESSBARS_TIMERS[jpb.id] =  setInterval(function(){
                if(t>=d){
                    clearInterval(timer);
                    jpb.value   =   value;
                    block[p]    =   textMask[p] = jpb.pct  =  Math.ceil(value/maxValue*100)+"%";
                    jpb.setText(text);
                    jpb.fireEvent("onProgressChange,onProgressed"+(jpb.pct=="100%"?",onComplete":""));
                    return;
                }
                jpb.value   =   oldValue+Math.ceil(EASING_EFFECT(t, b, c, d))*sign;
                t+=0.5;
                block[p]   =  textMask[p] = jpb.pct  =  Math.ceil(jpb.value/maxValue*100)+"%";
                jpb.setText(jpb.text);
                jpb.fireEvent("onProgressChange");
            },10);
        }else{
            jpb.value  =  value;
            block[p]   =  textMask[p] = jpb.pct  =  Math.ceil(value/jpb.maxValue*100)+"%";
            jpb.setText(jpb.text);
            jpb.fireEvent("onProgressChange,onProgressed"+(jpb.pct=="100%"?",onComplete":""));
        }
       return this;   
    }
    /**
     * JProgressBar.prototype.fireEvent 触发进度条中的事件
     * @param   {Array/String}  events  要触发的事件列表,字符串类型中。多个事件用","分开
     * @return  {Object}                返回当前进度条实例对象  
     */
    JProgressBar.prototype.fireEvent =  function(events){
        var fireEvent,jpb;
        jpb    =    this;
        events =    typeof events=="string"?events.split(","):events;
        setTimeout(function(){
            var event,i,l,args;
            if(!events)return;
            args    =   [jpb.value,jpb.pct,jpb.maxValue];
            for(i=0,l=events.length;i<l;i++){
                event   =   events[i];
                event=="onProgressChange"?jpb.onProgressChange.apply(jpb,args):jpb[event]();
            }
        },0);
        return this;
    }
    /**
     * JProgressBar.prototype.destroy 销毁组件
     */
    JProgressBar.prototype.destroy  =   function(){
        var onDestroy;
        this.onBeforeDestroy();
        if(ISIE){
            JPROGRESSBARS_DOC_FRAGMENT.appendChild(this.box);
            JPROGRESSBARS_DOC_FRAGMENT.innerHTML='';
        }else{
            this.parent.removeChild(this.box);
        }
        clearInterval(JPROGRESSBARS_TIMERS[this.id]);
        delete JPROGRESSBARS_TIMERS[this.id];
        delete JPROGRESSBARS[this.id];
        onDestroy = this.onDestroy;
        for(var m in this){	
            if(this.hasOwnProperty(m)){
                delete this[m];
            }
        }
        onDestroy.call(this);
    }
    /**
     * JProgressBar.setText             设置指定进度条实例中显示的文本
     * @param   {Object/String}   jpb   进度条实例对象,如果指定值是字符串类型则会被当做进度条的ID来获取进度条实现对象
     * @param   {String} text           进度条中显示的文字字符串，字符串中出现的{#maxValue},{#value},{#pct} 分别表示，同时也会被替换为 进度条的最大值，当前进度值，当前进度值的百分比值        
     * @return  {Object}                返回操作进度条实例对象
     */
    JProgressBar.setText    =   function(jpb,text){
        jpb         =   typeof jpb=="string"?JPROGRESSBARS[jpb]:jpb;
        if(!jpb)return;
        return jpb.setText(text);
    }
    /**
     * JprogressBar.setValue                设置指定进度条实例中的进度
     * @param   {Object/String}   jpb       进度条实例对象,如果指定值是字符串类型则会被当做进度条的ID来获取进度条实现对象
     * @param   {Number/Float}    value     进度条的值，接受类似于 100,'0.23%',253.23,'235%','.5%' 的值 
     * @param   {Boolean}         animation 指定改变进度条进度值是。是否采用动画效果，默认为true
     * @return  {Object}                    返回操作进度条实例对象
     */
    JProgressBar.setValue  = function(jpb,value,animation){
        jpb         =   typeof jpb=="string"?JPROGRESSBARS[jpb]:jpb;
        if(!jpb)return;
        return jpb.setValue(value,animation);
    }
    /**
     * JProgressBar.prototype.fireEvent     触发指定进度条中的事件
     * @param   {Object/String}   jpb       进度条实例对象,如果指定值是字符串类型则会被当做进度条的ID来获取进度条实现对象 
     * @param   {Array/String}  events      要触发的事件列表,字符串类型中。多个事件用","分开
     * @return  {Object}                    返回当前进度条实例对象  
     */
    JProgressBar.fireEvent = function(jpb,events){
        jpb         =   typeof jpb=="string"?JPROGRESSBARS[jpb]:jpb;
        if(!jpb)return;
        return jpb.fireEvent(events);
    }
    /**
     * JprogressBar.destroy   销毁指定进度条实例
     */
    JProgressBar.destroy = function(jpb){
        jpb         =   typeof jpb=="string"?JPROGRESSBARS[jpb]:jpb;
        if(!jpb)return;
        return jpb.destroy();
    }
    /**
     * JProgressBar.create      创建一个进度条，并添加到指定的元素内
     * @param {Object} config   进度条HTML模板中需要被替换的属性值
     *  config:{
     *      id:进度条ID,
     *      cls:自定义样式类,
     *      size:进度条的大小，格式 width:***px;height:***px;,
     *      direction:进度条方向
     *  }
     * @param {String/Element}  进度条所在父元素,如果未指定则会使用document.write 来创建   
     */
    JProgressBar.create = function(config,el){
        var jpbHtml;
        jpbHtml  =   JProgressBar.tpl(RES.HTMLTPL,config);
        
        el = el?(typeof el=="string"?DOM.getElementById(el):el):null;
        if(el){
            JPROGRESSBARS_DOC_FRAGMENT.innerHTML = jpbHtml;
            el.appendChild(JPROGRESSBARS_DOC_FRAGMENT.childNodes[0]);
        }else{
            document.write(jpbHtml);
        }
    }
    /**
     * JProgressBar.tpl         将提供的参数编译到字符串模板里并返回
     * @param   {String} tpl    字符串模板 
     * @param   {Object} keys   指定要被编译的属性值，模板中对属性值的格式是{#属性名};
     * @return  {String}        返回编译后的字符串
     */
    JProgressBar.tpl = function(tpl,keys){
        keys = keys||{};
        return tpl.replace(/\{\#([^#\}]+?)\}/g, function(all, key) {
            return keys[key]===undefined?"":keys[key];
        });
    }
    /**
     * JProgressBar.init 初始化进度条样式
     * 使用JProgressBar创建进动条之前应该先调用JProgressBar.init 函数。同一页面只需要一次就行。
     */
    JProgressBar.init = function(){
        var head,styleElement,styleSheets,addRule,styleSheet,rIndex,patt,rule,css;
        rIndex      = 0;
        css         = RES.CSS;
        patt        = /([^\{]*.*?)\{([^\}]*?)\}/g;
        styleSheets = DOM.styleSheets;
        if(!styleSheets.length){
            head                =   DOM.getElementsByTagName("HEAD")[0];
            styleElement        =   DOM.createElement('STYLE');
            styleElement.type   =   'text/css';
            (head||DOM).appendChild(styleElement);
            styleElement        =   head =  null;
        }
        styleSheet = styleSheets[0];
        addRule    = !ISIE?function(selector,css,rIndex,rule){styleSheet.insertRule(rule,rIndex)}:styleSheet.addRule;   
        var i,b;
        while((rule=patt.exec(css))){
           addRule(rule[1],rule[2],0,rule[0]);
        }
    }
    
    //Download by http://www.codefans.net
    
    ///////////////////////////////////////////////
    if(!window.console){
        window.console={log:F_NOOP};
    }
    JProgressBar.init();
    return JProgressBar;
});
