# AppleTV+ For Android

O [AppleTV Plus](https://www.apple.com/apple-tv-plus/) é um serviço de streaming de séries e filmes da Apple. Disponível para dispositivos iOS, MacOS, PlayStation, Xbox, smartTVs - inclusive com Android TV - entretanto, usuários de aparelhos Android precisam recorrer ao site para assistir ao conteúdo. Sendo assim, o AppleTV+ for Android é um aplicativo que permite assistir o conteúdo do AppleTV+ em um dispositivo Android a partir de um app instalável - sem precisar abrir o browser e procurar pelo site sempre que quiser assistir. 

![AppleTV](https://i.imgur.com/sONaPHw.jpg)

Por baixo da tela, o aplicativo __não é__ um app nativo! Apenas usa o navegador de internet de seu celular, sem a barra de endereço, para acessar o site original do Apple TV+ - local onde toda a navegação é feita -, faz alguns ajustes para facilitar a exibição do vídeo em tela cheia, bem como permitir o uso em alguns aparelhos e/ou navegadores que o site se encontra bloqueado.

Para instalá-lo e testar em seu celular, utilize o .APK disponibilizado nas releases do projeto. É necessário um aparelho com Android 5.0 Lollipop ou superior - durante os testes, a aplicação funcionou corretamente em um Samsung Galaxy Note 10.


## Tecnicamente…

O aplicativo nativo foi escrito inteiramente em Kotlin, em apenas uma tarde de Outubro/21. A fluência com a linguagem e com os conceitos da programação mobile foram adquiridos com outro projeto - MackenzieTIA. O aplicativo emprega uma WebView, habilita algumas opções para possibilitar o uso do site fora do navegador, configura um User-Agent para simular um Google Chrome rodando num Samsung Galaxy S8+ e possibilitar o acesso em outros aparelhos, assim como configura a criação de uma View secundária na qual o vídeo em tela cheia é exibido. Parte do aprendizado provém do projeto do [RachitShah02](https://github.com/RachitShah02/Webview-Video-Fullscreen) para exibir vídeos em tela cheia usando uma WebView no Android.
