# KChart
股票分析K线图<br>
某课程大作业，根据老师给的股票24小时成交量数据（一堆txt文件）生成股票K线图，并且比对样本K线图与其他k线图的相似度，并排序。<br>
显示用的struts2，生成k线图用的java jfreechart绘图库。<br>
相似度算法有三个：均值hash算法\levenshtein距离算法\某组员自己yy出来的算法<br>
项目托管在阿里云上了,地址是 http://www.xcfish.cn/KChart/<br>
因为是老师给的固定格式的数据，数据txt文件放在webroot/picList里面了，并没有做用户自己上传数据的功能，对比相似度前请先点击导入数据来生成k线图。
经费有限，单线程跑的,运行速度略慢...
