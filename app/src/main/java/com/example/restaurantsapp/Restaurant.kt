package com.example.restaurantsapp

data class Restaurant(
    val id: Int,
    val title: String,
    val description: String,
    var isFavorite: Boolean = false
)

val dummyRestaurants = listOf(
    Restaurant(
        0,
        "Alfredo foods",
        "At Alfredo's you will find that we host a multitude of delicious Italian flavors ingrained in our pasta's and pizza's"
    ),
    Restaurant(
        1,
        "The Whisk & Ladle",
"A cozy bistro specializing in French-inspired comfort food. Savor deliciously creamy soups, artisanal bread, and decadent desserts while enjoying the rustic charm of our candlelit dining room"    ),
    Restaurant(
        2,
        "Spice Fusion",
"Embark on a culinary journey through Asia at Spice Fusion. Experience the perfect blend of flavors from Thai, Indian, and Chinese cuisines, expertly crafted into mouthwatering dishes that will transport your taste buds."    ),
    Restaurant(
        3,
        "Bella Napoli Pizzeria",
"Indulge in the authentic taste of Italy at Bella Napoli. Our wood-fired pizzas, made with imported ingredients and traditional Neapolitan techniques, will transport you to the bustling streets of Naples."    ),
    Restaurant(
        4,
        "The Garden Grill",
"A haven for health-conscious foodies, The Garden Grill offers a delightful range of organic, farm-to-table dishes. Enjoy freshly harvested ingredients creatively prepared into nourishing salads, hearty bowls, and vibrant smoothies."    ),
    Restaurant(
        5,
        "Sea Salt & Vine",
"Set sail on a seafood adventure at Sea Salt & Vine. Immerse yourself in the ocean's bounty with our exquisite selection of freshly caught fish, succulent shellfish, and perfectly paired wines, all while overlooking breathtaking waterfront views."    ),
    Restaurant(
        6,
        "The Green Leaf Café",
" A plant-based paradise for vegans and vegetarians, The Green Leaf Café serves up a delightful array of innovative meat-free dishes. From colorful Buddha bowls to mouthwatering plant-based burgers, each creation celebrates the wonders of vegetables."    ),
    Restaurant(
        7,
        "Tandoori Tales",
"Experience the vibrant flavors of India at Tandoori Tales. Our aromatic spices and tandoor oven-cooked dishes will transport you to the bustling streets of Mumbai, where every bite is an explosion of taste and tradition."    ),
    Restaurant(
        8,
        "Le Boulangerie Patisserie",
"Step into a French patisserie where the air is filled with the aroma of freshly baked bread and pastries. Le Boulangerie Patisserie offers a delectable selection of croissants, macarons, and éclairs, paired perfectly with a cup of rich coffee."    ),
    Restaurant(
        9,
        "Smokehouse BBQ",
"For lovers of smoky, slow-cooked meats, Smokehouse BBQ is the ultimate destination. Indulge in tender ribs, juicy brisket, and mouthwatering pulled pork, all accompanied by our signature homemade barbecue sauces."    ),
    Restaurant(
        10,
        "Sushi Sensations",
" Dive into a world of sushi mastery at Sushi Sensations. Our skilled chefs create edible works of art using the freshest ingredients, presenting a fusion of traditional and contemporary sushi rolls that will delight both your eyes and palate."    ),
    Restaurant(
        11,
        "The Spice Market",
" Embark on a culinary adventure to the vibrant streets of Morocco, India, and beyond at The Spice Market. Immerse yourself in the rich aromas and bold flavors of North African and South Asian cuisine, where exotic spices reign supreme."    ),
    Restaurant(
        12,
        "La Dolce Vita Gelateria",
"Transport yourself to the sun-kissed streets of Italy with a visit to La Dolce Vita Gelateria. Indulge in creamy gelato crafted from traditional recipes, featuring luscious flavors like pistachio, tiramisu, and fruity sorbets."    ),
    Restaurant(
        13,
        "The Rustic Vineyard",
"Nestled amidst rolling vineyards, The Rustic Vineyard offers a memorable wine country dining experience. Pair exquisite local wines with artisanal cheese platters, freshly baked bread, and gourmet charcuterie, all while enjoying breathtaking vineyard views."    )
)
