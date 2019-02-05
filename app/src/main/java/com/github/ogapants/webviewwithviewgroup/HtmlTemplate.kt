package com.github.ogapants.webviewwithviewgroup

class HtmlTemplate {
    companion object {

        const val head: String = """
<!DOCTYPE html>
<html>
<body>
"""
        const val foot: String = """
</body>
</html>
"""
        const val body: String = """
1<br>
2<br>
3<br>
4<br>
5<br>
6<br>
7<br>
8<br>
9<br>
10<br>
11<br>
12<br>
13<br>
14<br>
15<br>
16<br>
17<br>
18<br>
19<br>
20<br>
21<br>
22<br>
23<br>
24<br>
25<br>
26<br>
27<br>
28<br>
29<br>
30<br>
31<br>
32<br>
33<br>
34<br>
35<br>
36<br>
37<br>
38<br>
39<br>
40<br>
41<br>
42<br>
43<br>
44<br>
45<br>
46<br>
47<br>
48<br>
49<br>
50<br>
51<br>
52<br>
53<br>
54<br>
55<br>
56<br>
57<br>
58<br>
59<br>
60<br>
61<br>
62<br>
63<br>
64<br>
65<br>
66<br>
67<br>
68<br>
69<br>
70<br>
71<br>
72<br>
73<br>
74<br>
75<br>
76<br>
77<br>
78<br>
79<br>
80<br>
81<br>
82<br>
83<br>
84<br>
85<br>
86<br>
87<br>
88<br>
89<br>
90<br>
91<br>
92<br>
93<br>
94<br>
95<br>
96<br>
97<br>
98<br>
99<br>
100<br>
101<br>
102<br>
103<br>
104<br>
105<br>
106<br>
107<br>
108<br>
109<br>
110<br>
111<br>
112<br>
113<br>
114<br>
115<br>
116<br>
117<br>
118<br>
119<br>
120<br>
121<br>
122<br>
123<br>
124<br>
125<br>
126<br>
127<br>
128<br>
129<br>
130<br>
131<br>
132<br>
133<br>
134<br>
135<br>
136<br>
137<br>
138<br>
139<br>
140<br>
141<br>
142<br>
143<br>
144<br>
145<br>
146<br>
147<br>
148<br>
149<br>
150<br>
151<br>
152<br>
153<br>
154<br>
155<br>
156<br>
157<br>
158<br>
159<br>
160<br>
161<br>
162<br>
163<br>
164<br>
165<br>
166<br>
167<br>
168<br>
169<br>
170<br>
171<br>
172<br>
173<br>
174<br>
175<br>
176<br>
177<br>
178<br>
179<br>
180<br>
181<br>
182<br>
183<br>
184<br>
185<br>
186<br>
187<br>
188<br>
189<br>
190<br>
191<br>
192<br>
193<br>
194<br>
195<br>
196<br>
197<br>
198<br>
199<br>
200<br>
201<br>
202<br>
203<br>
204<br>
205<br>
206<br>
207<br>
208<br>
209<br>
210<br>
211<br>
212<br>
213<br>
214<br>
215<br>
216<br>
217<br>
218<br>
219<br>
220<br>
221<br>
222<br>
223<br>
224<br>
225<br>
226<br>
227<br>
228<br>
229<br>
230<br>
231<br>
232<br>
233<br>
234<br>
235<br>
236<br>
237<br>
238<br>
239<br>
240<br>
241<br>
242<br>
243<br>
244<br>
245<br>
246<br>
247<br>
248<br>
249<br>
250<br>
251<br>
252<br>
253<br>
254<br>
255<br>
256<br>
257<br>
258<br>
259<br>
260<br>
"""
        const val simpleHtml: String = head + body + foot

        const val template: String = """
<!DOCTYPE html>
<html>
<style>
  .initial-load {
      /* 0x0 and 1x1 may be short-circuited by WebView */
      width: 2px;
      height: 2px;
      -webkit-transform: translate3d(0, 0, 1px);
      -webkit-animation-name: initial-load-noop-animation;
      -webkit-animation-duration: 1ms;  /* doesn't matter */
  }

  /* Animating the z-position is fast and does not actually change anything in the default
   * perspective.
   */
  @-webkit-keyframes initial-load-noop-animation {
      from {
          -webkit-transform: translate3d(0, 0, 1px);
      }
      to {
          -webkit-transform: translate3d(0, 0, 0);
      }
  }
</style>
<body>
<div id="header-spacer" style="height: %spx;"></div>
<div id="content">
%s
</div>
<div id="footer-spacer" style="height: %spx;"></div>
<div id="initial-load-signal" class="initial-load"></div>
</body>
<script type="text/javascript">
window.addEventListener("DOMContentLoaded", function () {
   var contentHeight = document.getElementById("content").offsetHeight;
   window.JS._onDomContentLoaded(contentHeight);
});
function onContentReady(event) {
    var contentHeight = document.getElementById("content").offsetHeight;
    window.JS._onContentReady(contentHeight);
}

function setupContentReady() {
    var signalDiv = document.getElementById("initial-load-signal");
    signalDiv.addEventListener("webkitAnimationStart", onContentReady, false);
}
setupContentReady();
</script>
</html>
"""

    }
}