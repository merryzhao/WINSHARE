define(function(require){
    var $ = require("jQuery"),
        pinyin = require("../jQuery/plugin/jquery.charfirst.pinyin"),
        listArea = {
            opts: { 
                initLetter: '', 
                includeAll: true, 
                incudeOther: false, 
                includeNums: true, 
                flagDisabled: true, 
                cookieName: null, 
                onClick: null, 
                prefixes: ['a-g'] 
            },
            letters: ['_', 'a-g', 'h-n', 'o-t', 'u-z'],
            selector:{
                title:"div#area-list-nav",
                list:"ul#area-list"
            },
            init:function(){
                this.title=$(this.selector.title);
                this.list=$(this.selector.list);
                this.createLetters();
                this.bind();
                
            },
            bind:function(){
                this.title.delegate(".tab-item a","click", function() {
                    var prevLetter, letter = $(this).data("letter"); 
                    $(".tab-item a.current").removeClass('current'); 
                    $(this).addClass('current'); 
                    if (letter == 'all') { 
                        listArea.list.children().show();  
                    }else {
                        if(!!prevLetter){
                            listArea.list.children('.ln-' + prevLetter).hide();
                        }else{
                            listArea.list.children().hide();  
                        }
                        listArea.list.children('.ln-' + letter).show(); 
                    }
                    prevLetter = letter;
                });
            },
            addClasses:function() { 
                var str, firstChar, firstWord, spl, $this, hasPrefixes = (this.opts.prefixes.length > 0); 
                this.list.children().each(function() {
                    $this = $(this), firstChar = '', str = $.trim($this.text()).toLowerCase(); 
                    if (str != '') {
                        if (hasPrefixes) { 
                            spl = str.split(' '); 
                            if ((spl.length > 1) && ($.inArray(spl[0], opts.prefixes) > -1)) { 
                                firstChar = spl[1].charAt(0); 
                                firstChar = pinyin.makePy(firstChar)[0].toLowerCase(); 
                                listArea.addLetterClass(firstChar, $this, true); 
                            } 
                        }
                        firstChar = str.charAt(0); 
                        firstChar = pinyin.makePy(firstChar)[0].toLowerCase(); 
                        listArea.addLetterClass(firstChar, $this);
                    } 
                });
            },
            addLetterClass:function(firstChar, $el, isPrefix) {
                if (/\W/.test(firstChar)) firstChar = '-'; 
                if (!isNaN(firstChar)) firstChar = '_'; 
                if(firstChar<="g"){
                    $el.addClass('ln-a-g');
                }else if(firstChar<="n"){
                    $el.addClass('ln-h-n');
                }else if(firstChar<="t"){
                    $el.addClass('ln-o-t');
                }else{
                    $el.addClass('ln-u-z');
                }
            },
            createLetters:function(){
                var html, arr = [], letters=this.letters; 
                for (var i = 1; i < letters.length; i++) { 
                    if (arr.length == 0) arr.push('<div class="tab-item"><a class="current all" data-letter="all" href="javascript:void(0);">全部</a></div>'); 
                    arr.push('<div class="tab-item"><a data-letter="' + letters[i] + '" href="javascript:void(0);">' + letters[i].toUpperCase() + '</a></div>'); 
                }
                html = '<div class="ln-letters">' + arr.join('') + '</div>';
                this.title.html(html);
            }
        };
    return listArea;
});