#상권분석
install.packages("xlsx")
library(xlsx)
store_data<-read.csv("201809_1.csv",header=T)

install.packages("dplyr")
library(dplyr)
sd1 <- store_data %>% 
  filter(시군구명=="관악구" & 상권업종중분류명=="분식")%>%
  group_by(행정동명) %>%
  summarise(n=n())%>%
  arrange(n)

sd1


#연령분석
library(readxl)
pop_data<-read_excel("popdata.xls")

install.packages("dplyr")
library(dplyr)

pd<- pop_data %>%
  filter(자치구=="관악구" & 구분 =="계")%>%
  group_by(행정동)%>%
  mutate(total=`15~19세`+`20~24세`)%>%
  select(행정동, total)%>%
  arrange(desc(total))
pd<- pd[pd$행정동!="소계",]
pd


#워드클라우드 분석 - 엽기떡볶이
install.packages("RCurl")
install.packages("XML")
install.packages("rJava")
library(rJava)
library(RCurl)
library(XML)
searchURL<-"https://openapi.naver.com/v1/search/blog.xml"
Client_ID<-"_eCPEdxG23CmuezdeNMz"
Client_Secret<-"l564A_pKXp"
query<-URLencode(iconv("엽떡","euc-kr","UTF-8"))
url<-paste(searchURL,"?query=",query,"&display=20",sep = "")
doc<-getURL(url,httpheader=c('Content-Type'="application/xml",'X-Naver-Client-Id'=Client_ID,'X-Naver-Client-Secret'=Client_Secret))
doc2<-htmlParse(doc,encoding = "UTF-8")
text<-xpathSApply(doc2,"//item/description",xmlValue)
text
install.packages("KoNLP")
install.packages("RColorBrewer")
install.packages("wordcloud")
library(KoNLP)
library(RColorBrewer)
library(wordcloud)
useSejongDic()
noun<-sapply(text,extractNoun,USE.NAMES = F)
noun2<-unlist(noun)
noun2<-gsub('\\d+','',noun2)
noun2<-gsub('<b>','',noun2)
noun2<-gsub('</b>','',noun2)
noun2<-gsub('&amp','',noun2)
noun2<-gsub('&lt','',noun2)
noun2<-gsub('&gt','',noun2)
noun2<-gsub('&quot','',noun2)
noun2<-gsub('"','',noun2)
noun2<-gsub('\'','',noun2)
noun2<-gsub(' ','',noun2)
noun2<-gsub('-','',noun2)
noun2
wordcount<-table(noun2)
head(sort(wordcount,decreasing = T),30)
palete<-brewer.pal(9,"Set1")
wordcloud(names(wordcount),freq = wordcount,scale = c(5,0.25),rot.per = 0.25,min.freq = 1,random.order = F,random.color = T,colors = palete)


#워드클라우드분석 - 죠스떡볶이
install.packages("RCurl")
install.packages("XML")
install.packages("rJava")
library(rJava)
library(RCurl)
library(XML)
searchURL<-"https://openapi.naver.com/v1/search/blog.xml"
Client_ID<-"_eCPEdxG23CmuezdeNMz"
Client_Secret<-"l564A_pKXp"
query<-URLencode(iconv("죠스","euc-kr","UTF-8"))
url<-paste(searchURL,"?query=",query,"&display=20",sep = "")
doc<-getURL(url,httpheader=c('Content-Type'="application/xml",'X-Naver-Client-Id'=Client_ID,'X-Naver-Client-Secret'=Client_Secret))
doc2<-htmlParse(doc,encoding = "UTF-8")
text<-xpathSApply(doc2,"//item/description",xmlValue)
text
install.packages("KoNLP")
install.packages("RColorBrewer")
install.packages("wordcloud")
library(KoNLP)
library(RColorBrewer)
library(wordcloud)
useSejongDic()
noun<-sapply(text,extractNoun,USE.NAMES = F)
noun2<-unlist(noun)
noun2<-gsub('\\d+','',noun2)
noun2<-gsub('<b>','',noun2)
noun2<-gsub('</b>','',noun2)
noun2<-gsub('&amp','',noun2)
noun2<-gsub('&lt','',noun2)
noun2<-gsub('&gt','',noun2)
noun2<-gsub('&quot','',noun2)
noun2<-gsub('"','',noun2)
noun2<-gsub('\'','',noun2)
noun2<-gsub(' ','',noun2)
noun2<-gsub('-','',noun2)
noun2
wordcount<-table(noun2)
head(sort(wordcount,decreasing = T),30)
palete<-brewer.pal(9,"Set1")
wordcloud(names(wordcount),freq = wordcount,scale = c(5,0.25),rot.per = 0.25,min.freq = 1,random.order = F,random.color = T,colors = palete)


#워드클라우드분석 - 신전떡볶이
install.packages("RCurl")
install.packages("XML")
install.packages("rJava")
library(rJava)
library(RCurl)
library(XML)
searchURL<-"https://openapi.naver.com/v1/search/blog.xml"
Client_ID<-"_eCPEdxG23CmuezdeNMz"
Client_Secret<-"l564A_pKXp"
query<-URLencode(iconv("신전","euc-kr","UTF-8"))
url<-paste(searchURL,"?query=",query,"&display=20",sep = "")
doc<-getURL(url,httpheader=c('Content-Type'="application/xml",'X-Naver-Client-Id'=Client_ID,'X-Naver-Client-Secret'=Client_Secret))
doc2<-htmlParse(doc,encoding = "UTF-8")
text<-xpathSApply(doc2,"//item/description",xmlValue)
text
install.packages("KoNLP")
install.packages("RColorBrewer")
install.packages("wordcloud")
library(KoNLP)
library(RColorBrewer)
library(wordcloud)
useSejongDic()
noun<-sapply(text,extractNoun,USE.NAMES = F)
noun2<-unlist(noun)
noun2<-gsub('\\d+','',noun2)
noun2<-gsub('<b>','',noun2)
noun2<-gsub('</b>','',noun2)
noun2<-gsub('&amp','',noun2)
noun2<-gsub('&lt','',noun2)
noun2<-gsub('&gt','',noun2)
noun2<-gsub('&quot','',noun2)
noun2<-gsub('"','',noun2)
noun2<-gsub('\'','',noun2)
noun2<-gsub(' ','',noun2)
noun2<-gsub('-','',noun2)
noun2
wordcount<-table(noun2)
head(sort(wordcount,decreasing = T),30)
palete<-brewer.pal(9,"Set1")
wordcloud(names(wordcount),freq = wordcount,scale = c(5,0.25),rot.per = 0.25,min.freq = 1,random.order = F,random.color = T,colors = palete)

