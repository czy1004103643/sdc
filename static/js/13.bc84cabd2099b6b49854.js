webpackJsonp([13],{"2mdu":function(n,t,i){t=n.exports=i("FZ+f")(!0),t.push([n.i,"\n.news-list .no-data[data-v-190ed5d4] {\n  padding-top: 200px;\n}\n.news-item[data-v-190ed5d4] {\n  background: #fff;\n  margin-bottom: 5px;\n  padding: 20px 16px;\n  position: relative;\n  min-height: 60px;\n  padding-right: 20px;\n}\n.news-item.news-img[data-v-190ed5d4] {\n    min-height: 80px;\n    padding-right: 120px;\n}\n.news-item.news-img .img[data-v-190ed5d4] {\n      position: absolute;\n      width: 80px;\n      height: 80px;\n      right: 20px;\n      top: 20px;\n}\n.news-item.news-img .img img[data-v-190ed5d4] {\n        width: 100%;\n        height: 100%;\n}\n.news-item .title[data-v-190ed5d4] {\n    font-size: 14px;\n    color: #373d41;\n}\n.news-item .author[data-v-190ed5d4],\n  .news-item .time[data-v-190ed5d4] {\n    color: #aaa;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/news/index.vue"],names:[],mappings:";AACA;EACE,mBAAmB;CACpB;AACD;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,mBAAmB;EACnB,iBAAiB;EACjB,oBAAoB;CACrB;AACD;IACI,iBAAiB;IACjB,qBAAqB;CACxB;AACD;MACM,mBAAmB;MACnB,YAAY;MACZ,aAAa;MACb,YAAY;MACZ,UAAU;CACf;AACD;QACQ,YAAY;QACZ,aAAa;CACpB;AACD;IACI,gBAAgB;IAChB,eAAe;CAClB;AACD;;IAEI,YAAY;CACf",file:"index.vue",sourcesContent:["\n.news-list .no-data[data-v-190ed5d4] {\n  padding-top: 200px;\n}\n.news-item[data-v-190ed5d4] {\n  background: #fff;\n  margin-bottom: 5px;\n  padding: 20px 16px;\n  position: relative;\n  min-height: 60px;\n  padding-right: 20px;\n}\n.news-item.news-img[data-v-190ed5d4] {\n    min-height: 80px;\n    padding-right: 120px;\n}\n.news-item.news-img .img[data-v-190ed5d4] {\n      position: absolute;\n      width: 80px;\n      height: 80px;\n      right: 20px;\n      top: 20px;\n}\n.news-item.news-img .img img[data-v-190ed5d4] {\n        width: 100%;\n        height: 100%;\n}\n.news-item .title[data-v-190ed5d4] {\n    font-size: 14px;\n    color: #373d41;\n}\n.news-item .author[data-v-190ed5d4],\n  .news-item .time[data-v-190ed5d4] {\n    color: #aaa;\n}\n"],sourceRoot:""}])},ZCNk:function(n,t,i){"use strict";function e(n){i("xbxy")}Object.defineProperty(t,"__esModule",{value:!0});var a=i("8pLc"),s=i("KgXo"),d=(a.a,s.a,{name:"news",components:{noData:a.a,loading:s.a},data:function(){return{loading:!1,newsList:[]}},computed:{noNews:function(){return 0===this.newsList.length}},mounted:function(){this.getList()},methods:{getList:function(){this.loading=!0;var n={id:"1",title:"“大众点评”即将消失：被改名或被直接放弃",author:"春雨",img:"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1734052457,4245591854&fm=173&app=49&f=JPEG?w=218&h=146&s=61E22FE7427515960720A12903006053",time:"2019-01-01"},t={id:"2",title:"日本提出向中国人收“二维码使用费”？内容不实日本提出向中国人收“二维码使用费”？内容不实",author:"是大润发",img:"",time:"2019-01-02"};this.newsList=[n,n,t,n,n,t,n,n],this.loading=!1},jumpToDetail:function(n){this.$router.push({path:"/news/"+n})}}}),o=function(){var n=this,t=n.$createElement,i=n._self._c||t;return i("div",{staticClass:"news-list"},[n._l(n.newsList,function(t){return n.loading?n._e():i("div",{staticClass:"news-item",class:{"news-img":""!==t.img},on:{click:function(i){i.stopPropagation(),n.jumpToDetail(t.id)}}},[i("div",{staticClass:"title"},[n._v(n._s(t.title))]),n._v(" "),i("div",{staticClass:"author"},[n._v(n._s(t.author))]),n._v(" "),i("div",{staticClass:"time"},[n._v(n._s(t.time))]),n._v(" "),""!==t.img?i("div",{staticClass:"img"},[i("img",{attrs:{src:t.img}})]):n._e()])}),n._v(" "),n.loading?[i("loading")]:n._e(),n._v(" "),!n.loading&&n.noNews?i("div",{staticClass:"no-course"},[i("no-data",{attrs:{text:"当前没有技术资讯"}})],1):n._e()],2)},A=[],m={render:o,staticRenderFns:A},p=m,g=i("VU/8"),r=e,l=g(d,p,!1,r,"data-v-190ed5d4",null);t.default=l.exports},xbxy:function(n,t,i){var e=i("2mdu");"string"==typeof e&&(e=[[n.i,e,""]]),e.locals&&(n.exports=e.locals);i("rjj0")("24352536",e,!0,{})}});
//# sourceMappingURL=13.bc84cabd2099b6b49854.js.map