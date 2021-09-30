# Android-2021-Task-5

![Cat's API](https://cdn2.thecatapi.com/logos/thecatapi_256xW.png)

## Downloading and saving cats!

### Use this API: https://docs.thecatapi.com/ to implement the app with the following features:
1. Ability to download and show cats in a list. (Use Glide or Coil)
2. Pagination (your recycler should upload fresh portion of cats every time it reaches the end)
3. Detailed view. After clicking the cat in the recycler “full” image should be opened with card flip animation.
4. User could save the image to the Gallery.

### Use Retrofit to call REST API

### Use static code analyzers(detekt and ktlint)

### Optional:
1. You can try Kotlin scripts for build system.
2. Cover logic with unit tests (Spek2 + Mockk)

## Instructions
To use an authorized API, add your key to the `apikey.properties` file in the project's root folder:
```
CAT_API_KEY="<your The Cat API key>"
```
