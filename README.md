# Marvel Comics App
Android app containing two screens:
1. A list of all possible superheroes in the Marvel Multiverse (includes a search bar so users can search for a superhero by name)
2. A superhero detail page which includes a list of that hero's comics

The app is built using [Jetpack Compose](https://developer.android.com/jetpack/compose), Android's modern declarative UI toolkit.

### Architecture
- MVVM architecture
- [Kotlin multiplatform](https://kotlinlang.org/lp/mobile/) app, albeit one where only the Android app has been built
- Networking logic sits in the `shared` folder and could be reused by an iOS app
- Android specific code sits in the `androidApp` folder
- `iosApp` folder can be ignored

### App structure (and easiest way to review)
In the `androidApp` folder:
- `MainActivity` (manages the app's lifecycle and navigation)
- `CharacterListScreen` and `CharacterDetailScreen` contain the UI for the two screens
- `MarvelComicsViewModel` stores and manages UI-related data in a lifecycle conscious way

In the `shared` business logic folder:
- `MarvelComicsRepository` manages collecting data and exposing that data to the rest of the app
- `MarvelComicsApi` fetches data from the Marvel API
- Data classes (models) inc. `MarvelCharacter`, `Comic` etc.

### Performance
Both screens use Jetpack Compose's `LazyColumn` component, which only composes and lays out items which are visible in the component’s viewport. It also lazily manages the fetching of images for visible characters or comics from the Marvel API.

#### Character List screen:
I've made a conscious decision to sacrifice a little in performance for a boost in usability.

The first time the app is opened, it fetches all of the characters from the Marvel API and caches them in a SQLite database on the user's device. Only strings are stored, so the storage required is negligible. The app will only refetch data from the Marvel API every 7 days (it's rare that they create a new character).

The advantage of this approach is that it enables offline use of the app and no further API calls are required when a user is searching for a particular superhero.

#### Character Detail screen:
The character's comics are fetched from the Marvel API each time a user clicks on a given character. It didn't make sense to locally store all of the comics for 2000+ characters.

### Tests
- Unit tests can be found in `androidApp/src/test` and `shared/src/commonTest`.
- UI tests are in `androidApp/src/androidTest`

### Dependency injection
The app uses the [Koin](https://insert-koin.io/) Kotlin injection library.

### Animations
Simple `slideInHorizontally`/`slideOutHorizontally` animations for the screen transitions.

## Next steps given more time:
- Increase unit test coverage, particularly of the view model
- Add a loading animation or logo to the splash screen
- Build an iOS app using the `shared` business logic

### Video - Light mode
https://user-images.githubusercontent.com/13498475/156947138-4765c1fd-d1c4-49ec-945c-e1c85f8aca7c.mp4

### Video - Dark mode
https://user-images.githubusercontent.com/13498475/156947159-31ad57bc-810e-4b63-9ded-f4918298966b.mp4
