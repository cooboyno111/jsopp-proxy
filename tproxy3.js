//原型链+编译器实现proxy 的getter setter 兼容所有浏览器。
//只有一个原则当你使用new_Proxy建立的对象的时候使用:来访问其属性和方法。你可以理解为编译器重载了:.
//其:会在编译过程中自动替换成_set，_get，或. 。
Object.prototype.pget=undefined;
Object.prototype.pdel=undefined;
Object.prototype.pset=undefined;
Object.prototype._get=undefined;
Object.prototype._del=undefined;
Object.prototype._set=undefined;
Object.prototype.proxyObj=false;
function new_Proxy(obj,proxy){
if(proxy.get==undefined){
     obj.pget=function(target,prop){return target[prop];};
}else{
     obj.pget=proxy.get;
}
if(proxy.deleteProperty==undefined){
     obj.pdel=function(target,prop){delete target[prop];};
}else{
     obj.pdel=proxy.deleteProperty;
}
if(proxy.set==undefined){
     obj.pset=function(target,prop,value){target[prop]=value};
}else{
     obj.pset=proxy.set;
}
obj._get=function(key){return this.pget(this,key)};
obj._del=function(key){ this.pdel(this,key)};
obj._set=function(key,val){return this.pset(this,key,val)};
obj.proxyObj=true;
return obj;
}
var ss={};
ss.fuck=function(){console.log('fuck');}
var pss=new_Proxy(ss,{
        get: function (target,prop) {
            console.log('数据获取', prop);
            return target[prop];
        },
        set: function (target,prop,value) {
            console.log('数据更新', prop);
            target[prop]=value;
        },
        deleteProperty : function (target,prop) {
            console.log('数据删除', prop);
            delete target[prop];
        }
    });
pss._set('ss',200);
pss._set('name',100);
console.log(pss._get('ss'));
console.log(pss._get('name'));
pss.fuck();
pss._get('name')
pss._get('name');
if (pss._get('name')==100) {

}
if (pss._get('name')>=100) {

}
if (pss._get('name')<=100) {

}
if (pss._get('name')!=100) {

}
if (pss._get('name')>100) {

}
if (pss._get('name')<100) {

}
if (pss._get('name')===100) {

}
if (pss._get('name')!==100) {

}
if (100!==pss._get('name')) {

}

if ("ddd"!==pss._get('name')) {

}
var a=pss._get('ss')+100;
a=pss._get('ss')-100;
a=pss._get('ss')*100;
a=pss._get('ss')/100;
a=pss._get('ss')%100;
a=100+pss._get('ss')
a=100-pss._get('ss')
a=100*pss._get('ss')
a=100/pss._get('ss')
a=100%pss._get('ss')
pss._set('ss',a);
//pss._get('ss')++;//不支持
//pss._get('ss')--;//不支持
//pss._get('ss')+=100;//不支持
//pss._get('ss')-=100;//不支持
console.log(pss._get('ss')||0);
if ((pss._get('name')||0)||
    (pss._get('ss')||0)) 
{

}
console.log(pss._get('ss')&&0);
if ((pss._get('name')&&0)||
    (pss._get('ss')&&0))
{

}
 pss._del('name');
console.log(pss._get('name'));
