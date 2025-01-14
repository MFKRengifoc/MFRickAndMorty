# Rick And Morty App!

This is a Rick and Morty App which shows all information about the Series.

# Overview
With this app you can:
- Search characters by name and more filters
- Get information about **characters** : Appers in Episodes, Origin and Type
- Get information about **episodes** of the Series (until 5 Season)
- Get information about the **locations** that our favourites characters have visited
- **Test your fan level**. You can complete a test about the Rick And Morty Series to check your fan level

**More information** can be found in https://rickandmortyapi.com


# Dependencies

- *ViewModel*: To add the **MVVM** patron in the project.
- *Koin*: To add the **dependency injection**: Repositories, Databases.
- *~Hilt-Dagger~*: To add the **dependency injection**: Repositories, Databases. (Replaced by Koin)
- *~Hilt Testing~*: For **testing** HTTPS connections and Database Processing. (Temporally removed)
- *Coroutines*: For implement the HTTPS request with concurrency.
- *Coil*: To manage image requests.
- *Retrofit*: To manage **HTTPS request**.
- *OkHttp*: HTTPS client.
- *Gson converter*: To parse **JSON** payloads.
- *Room*: To manage user preferences and favourites characters using **SQLite API**.
- *Navigation*: Use to navigate acroos the app screens.
- *Lottie*: To add animations loadings states.
