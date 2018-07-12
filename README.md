# jsopp-proxy

原型链+编译器实现类似 ES6 proxy 的getter setter delete兼容所有浏览器 包括IE8。其本质是变参数调用为方法调用。

只有一个原则当你使用new_Proxy建立的对象的时候使用->来访问其属性和方法。你可以理解为编译器重载了->.

其->会在编译过程中自动替换成_set，_get，或. 。

使用条件：1 一行只支持一个 ->。2 不要使用++，--，+=，-= 目前不支持。

使用方法：java -jar jsopp.jar tproxy.js tproxy3.js

源码见jsprocess.java

tproxy代码用例里以包含了大多数可能使用到的情况。

此项目纯属是挑战不可能（IE8 不支持getter setter delete）的挑战之作。

添加了delete 操作符;其最终的效果要比Object.defineProperty优越一点了。支持不定义属性情况下拦截，支持拦截delete操作符。

之后可以扩展一下编译器可选择编译为ES5/ES6模式。当然ES6模式直接使用原生的Proxy。
