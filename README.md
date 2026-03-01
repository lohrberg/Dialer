📞 Android Dialer-app

En egenutvecklad Android-applikation byggd i Kotlin med fokus på modern
arkitektur, struktur och användarinteraktion. Projektet demonstrerar
MVVM, Room-databas, runtime permissions, custom views och Google
Maps-integration.

------------------------------------------------------------------------

🚀 FUNKTIONALITET

📱 NAVIGERING 
- Hem-vy
- Dial-vy
- Samtalshistorik
- Kartvy
- Inställningar
- Om-sida

🔢 CUSTOM DIALPAD 
- Egen DialpadButton med:
- Custom XML-attribut
- Automatisk textskalning
- Visuell tryck-animation
- Återanvändbar dialpad-komponent (12 knappar)
- Anpassad layout för portrait och landscape
- Ljudfeedback via SoundPool

📞 SAMTAL 
- Runtime permission för CALL_PHONE
- Säker fallback till ACTION_DIAL
- Förklaringsdialog vid nekad behörighet

🗂 SAMTALSHISTORIK 
- Persistens via Room
- MVVM-struktur:
- Entity
- DAO
- Repository
- ViewModel + LiveData
- Visning med RecyclerView
- Datum/tidsformat
- Empty state-hantering

📍KARTINTEGRATION 
- Position via FusedLocationProviderClient
- Lifecycle-medveten hantering av location updates
- Markörer i Google Maps med:
- Telefonnummer & Tidsstämpel

------------------------------------------------------------------------

🏗 ARKITEKTUR

Projektet är uppbyggt enligt MVVM för tydlig separation mellan UI och
logik.

Model -> Room-databas och repository ViewModel -> UI-state och LiveData
View -> Activities och XML

Detta ger bättre struktur, testbarhet och underhållbarhet.

------------------------------------------------------------------------

🛠 TEKNIKER

-   Kotlin
-   MVVM
-   Room
-   RecyclerView
-   Google Maps SDK
-   FusedLocationProviderClient
-   SoundPool
-   Git

------------------------------------------------------------------------

⚙️ INSTALLATION

1.  Klona repot:
   ```bash
   git clone https://github.com/lohrberg/Dialer.git
   ```

3.  Öppna i Android Studio.

4.  Lägg till Google Maps API-nyckel i manifest

5.  Synka Gradle och kör på emulator eller fysisk enhet.

------------------------------------------------------------------------

🧠 LÄRDOMAR

-   Skapande av återanvändbara custom views
-   Implementering av MVVM i Android
-   Säker hantering av runtime permissions
-   Reaktiv UI med Room + LiveData
-   Integration av kartor och platsdata
