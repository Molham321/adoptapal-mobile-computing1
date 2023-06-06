package de.fhe.adoptapal.data

import de.fhe.adoptapal.R
import de.fhe.adoptapal.model.Animal
import de.fhe.adoptapal.model.Owner

object FakeDatabase {
    val owner = Owner("Spikey Sanju", "Developer & Pet Lover", R.drawable.user)
    val AnimalList = listOf(
        Animal(
            0,
            "Hachiko",
            3.5,
            "Male",
            "Brown",
            12.9,
            "389m away",
            R.drawable.hund,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Boxer",
            "Hund"
        ),
        Animal(
            1,
            "Skooby Doo",
            3.5,
            "Male",
            "Gold",
            12.4,
            "412m away",
            R.drawable.red_dog,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Dackel",
            "Hund"
        ),
        Animal(
            2,
            "Miss Agnes",
            3.5,
            "Female",
            "White",
            9.6,
            "879m away",
            R.drawable.white_dog,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Rottweiler",
            "Hund"
        ),
        Animal(
            3,
            "Cyrus",
            3.5,
            "Male",
            "Black",
            8.2,
            "672m away",
            R.drawable.hund,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Pudel",
            "Hund"
        ),
        Animal(
            4,
            "Shelby",
            3.5,
            "Female",
            "Choco",
            14.9,
            "982m away",
            R.drawable.white_dog,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Dobermann",
            "Hund"
        ),
        Animal(
            5,
            "Hachiko",
            3.5,
            "Male",
            "Brown",
            12.9,
            "389m away",
            R.drawable.hund,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Boxer",
            "Hund"
        ),
        Animal(
            6,
            "Skooby Doo",
            3.5,
            "Male",
            "Gold",
            12.4,
            "412m away",
            R.drawable.red_dog,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Dackel",
            "Hund"
        ),
        Animal(
            7,
            "Miss Agnes",
            3.5,
            "Female",
            "White",
            9.6,
            "879m away",
            R.drawable.white_dog,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Rottweiler",
            "Hund"
        ),
        Animal(
            8,
            "Cyrus",
            3.5,
            "Male",
            "Black",
            8.2,
            "672m away",
            R.drawable.hund,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Pudel",
            "Hund"
        ),
        Animal(
            9,
            "Shelby",
            3.5,
            "Female",
            "Choco",
            14.9,
            "982m away",
            R.drawable.white_dog,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,",
            owner,
            "Dobermann",
            "Hund"
        )
    )
}