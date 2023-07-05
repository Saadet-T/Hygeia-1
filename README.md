# Hygeia Zafiyetli Web Uygulaması
<h3>Saadet Elif Tokuoğlu </h3> <a href="https://www.linkedin.com/in/eren-uğurlu-a601941a8/">https://www.linkedin.com/in/saadet-elif-tokuoğlu-ab2a59188/</a>

<h3>Eren Uğurlu</h3> <a href="https://www.linkedin.com/in/eren-uğurlu-a601941a8/">https://www.linkedin.com/in/eren-uğurlu-a601941a8/</a>

Bitirme projesi olarak iki kişi zafiyetli bir web uygulaması geliştirdik. Projedeki ana amacımız
raporlanabilir ve sektördeki zafiyetlere daha yakın bir uygulama gerçekleştirmekti böylece sektöre CTF
çözerek girmeye çalışan arkadaşlara daha gerçekçi bir alıştırma ortamı oluşturmak istedik . Backendde
SpringBoot frontend için ise Thymeleaf veri tabanı olarak PostgreSQL kullandık . 

Web Uygulamasında bulunan zafiyetler aşağıdaki gibidir;

•SQL Injection x2

•Improper authentication via Weak Signing Key

•Sensitive Data Exposure

•Broken Access Control

•Insecure Direct Object Reference (IDOR) x2

•Cross Site Scripting

•Security Misconfiguration

•Cross Site Request Forgery

•Insecure Design

•File Upload

•Mass Assignment

•Security Logging and Monitoring Failures 

•OS Command Injection

•Open Redirect 


<h1>Projenin ana yapısı;</h1>

<h2>-Kullanıcı Doğrulama ve Yetkilendirme Sistemi :</h2>

![image](https://github.com/ErenUgurlu/Hygeia/assets/68515706/1c1b0793-a2ca-45f4-b8d3-9e18a60b702f)


<h2>-Ürün Listeleme ve Filtreleme:</h2>

![image](https://github.com/ErenUgurlu/Hygeia/assets/68515706/4d5f9aaf-9e01-4454-a377-b32e82ca5da9)


<h2>-Sepet Sistemi:</h2>

![image](https://github.com/ErenUgurlu/Hygeia/assets/68515706/c066be38-dc2a-4511-8cd2-ddd08e3b669c)


<h2>-İletişim Sayfası:</h2>

![image](https://github.com/ErenUgurlu/Hygeia/assets/68515706/eb829607-ac17-4600-bb6e-c8a59c3e11d0)


<h2>-Hesabım ve Kullanıcı Bilgilerinin değiştirilmesi:</h2>

![image](https://github.com/ErenUgurlu/Hygeia/assets/68515706/a8543d3d-e639-4b83-845d-4427c43aa83c)


<h2>-Admin Özel Yönetim Paneli:</h2>

![image](https://github.com/ErenUgurlu/Hygeia/assets/68515706/a619a993-620c-4d6b-bb7b-d1be6af36799)

<h1>Zafiyet örnekleri;</h1>
Aşağıdaki linkler üzerinden bitirme çalışmamızdan seçtiğimiz bazı zafiyetlerin açıklamasına erişebilirsiniz;

<h3>SQLi:</h3><a href="https://github.com/Saadet-T/HYGEIAVULN/blob/main/SQL.pdf">SQLi Dokümantasyon</a>
<h3>Improper authentication via Weak Signing Key:</h3><a href="https://github.com/Saadet-T/HYGEIAVULN/blob/main/Improper%20authentication%20via%20Weak%20Signing%20Key.pdf">Improper authentication via Weak Signing Key Dokümantasyon</a>
<h3>IDOR:</h3><a href="https://github.com/Saadet-T/HYGEIAVULN/blob/main/IDOR.pdf">IDOR DOkümantasyon</a>
<h3>Security Misconfiguration:</h3><a href="https://github.com/Saadet-T/HYGEIAVULN/blob/main/Security%20Misconfiguration.pdf">Security Misconfiguration Dokümantasyon</a>
