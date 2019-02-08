# WebViewWithViewGroup

Sample for DroidKaigi2019 session
"WebView+ViewGroupを実現するAOSPメールアプリの内部実装とニュースアプリへの応用" 
"Internal implemention of AOSP mail app that makes WebView and ViewGroup possible and how to apply it to news apps"  
https://droidkaigi.jp/2019/timetable/70923

##　Before

using Pixel2XL emulator
```xml
<ScrollView>         
  <LinerLayout>      
     <Button>        
     <WebView>       
     <Button>        
   </LinerLayout>    
<ScrollView>         
```
<img src="https://user-images.githubusercontent.com/11440952/52453048-f192ff00-2b88-11e9-8f8e-b74073f7acc4.png" width="200">


## After
```xml
<ArticleContainer>  
   <ArticleWebView> 
   <Button>         
   <Button>         
</ArticleContainer> 
```

<img src="https://user-images.githubusercontent.com/11440952/52453049-f192ff00-2b88-11e9-940e-622e56c7fe18.png" width="200">
