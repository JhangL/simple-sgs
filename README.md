# sgs 三国杀

#### 介绍
一个简单的三国杀（标准版）卡牌游戏的系统。
正在开发中。。。

#### 软件架构
设计是将执行系统和交互系统分开进行开发，执行系统只关注于流程的执行，并将要玩家交互的事件放出，供交互系统使用。交互系统主要为实现各端的服务对接，主要处理来自执行系统的事件。
交互系统默认实现了一个简单的控制台交互。也主要作为测试使用。


#### 使用教程

1.  [sgs-run-console](sgs-run-console)模块下执行[StartGameInConsole](sgs-run-console%2Fsrc%2Fmain%2Fjava%2Fcom%2Fjh%2Fsgs%2FStartGameInConsole.java).main()方法开始控制台交互方式的系统
2.  通过[sgs-core](sgs-core)模块下[GameLauncher](sgs-core%2Fsrc%2Fmain%2Fjava%2Fcom%2Fjh%2Fsgs%2Fcore%2FGameLauncher.java).run()方法可开启一个执行系统，需实现一个简单的交互接口（系统可能因为事件未处理一直处于等待状态）

#### 问题
