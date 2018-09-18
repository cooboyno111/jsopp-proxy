# jsopp-proxy

原型链+编译器实现类似 ES6 proxy 的getter setter delete兼容所有浏览器 包括IE8。其本质是变参数调用为方法调用。

只有一个原则当你使用new_Proxy建立的对象的时候使用->来访问其属性和方法。你可以理解为编译器重载了->.

其->会在编译过程中自动替换成_set，_get，或. 。

使用条件：1 一行只支持一个 ->。2 不要使用++，-- 。3 复合赋值运算符只支持五种 +=，-=，*=，/=，%= 其他的不支持。

使用方法：

java -jar jsopp.jar tproxy.js tproxy_es5.js ES5

java -jar jsopp.jar tproxy.js tproxy_es6.js ES6

源码见jsprocess.java

tproxy代码用例里以包含了大多数可能使用到的情况。

此项目是在ES5基础上实现部份ES6 Proxy功能的实验作品，未使用到ES5 Object.defineProperty意味着可以支持IE8。

添加了delete 操作符，其使用效果要比ES5 Object.defineProperty好。

在只使用基础拦截器的情况下（get set delete）可以直接使用ES6 Proxy方式进行开发，不用转换思维到Object.defineProperty。保证代码的鲁棒性和可读性。

多数情况下接近ES6 Proxy。支持不定义属性情况下拦截，支持拦截delete操作符。

编译器可选择编译为ES5/ES6模式。ES6模式将直接使用原生的Proxy。

可以实现proxy同样的代码编译到不同平台且执行结果一致。
