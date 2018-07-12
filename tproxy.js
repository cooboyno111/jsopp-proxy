var ss={};
ss.fuck=function(){console.log('fuck');}
var pss=new Proxy(ss,{
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
pss->ss=200;
pss->name=100;
console.log(pss->ss);
console.log(pss->name);
pss->fuck();
pss->name
pss->name;
if (pss->name==100) {

}
if (pss->name>=100) {

}
if (pss->name<=100) {

}
if (pss->name!=100) {

}
if (pss->name>100) {

}
if (pss->name<100) {

}
if (pss->name===100) {

}
if (pss->name!==100) {

}
if (100!==pss->name) {

}

if ("ddd"!==pss->name) {

}
var a=pss->ss+100;
a=pss->ss-100;
a=pss->ss*100;
a=pss->ss/100;
a=pss->ss%100;
a=100+pss->ss
a=100-pss->ss
a=100*pss->ss
a=100/pss->ss
a=100%pss->ss
pss->ss=a;
//pss->ss++;//不支持
//pss->ss--;//不支持
pss->ss*=10;
pss->ss+=100;
pss->ss-=100;
pss->ss/=1;
pss->ss%=1;
console.log(pss->ss||0);
if ((pss->name||0)||
    (pss->ss||0)) 
{

}
console.log(pss->ss&&0);
if ((pss->name&&0)||
    (pss->ss&&0))
{

}
delete pss->name;
console.log(pss->name);
