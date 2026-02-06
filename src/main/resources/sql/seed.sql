INSERT INTO users (name, email) VALUES
    ("Frank", "Frank.Bank@gmail.com"),
    ("Hanna", "Hanna.Montanna@gmail.com"),
    ("Søren", "Søren.Sørensen@gmail.com"),
    ("Fleming", "Fleming.Demning@gmail.com"),
    ("VU", "VU.SenSei@EI-ya.com");


INSERT INTO movies (name, genre, length, description) values
    ("IronLung", "horror", "2:07","A convict explores a blood ocean on a desolate moon using a submarine called the Iron Lung to search for missing stars/planets"),
    ("Inception", "Sci-fi", "2:28", "A skilled thief enters people’s dreams to steal secrets — until he’s tasked with planting an idea instead."),
    ("The shawshank Redemption", "Drama", "2:22","The story of an innocent man’s decades-long struggle for freedom inside a corrupt prison system."),
    ("The Dark Knight ", "Action", "2:32","Batman faces his greatest enemy yet as the Joker plunges Gotham into chaos."),
    ("Spy x Family Code: White", "Action", "1:50","The Forgers go on a family trip after Loid suggests that Anya make a traditional dessert for a school competition connected to his mission. But their peaceful vacation quickly turns chaotic when Anya accidentally gets involved in a secret plot that could threaten world peace. Expect lots of comedy, action, and wholesome family moments!"),
    ("Avatar", "Sci-Fi", "2:42","A paralyzed soldier enters an alien world and becomes part of a tribe he was meant to betray."),
    ("Howl’s Moving Castle", "Fantasy", "1:59","A young woman cursed into old age seeks help from a mysterious wizard living in a walking castle."),
    ("My Neighbor Totoro", "Family", "1:26","Two sisters meet magical forest creatures, including the iconic Totoro, while exploring their new rural home."),
    ("Kiki’s Delivery Service", "Fantasy", "1:43","A young witch starts her own delivery service in a seaside town and learns to stand on her own."),
    ("ponyo", "Family", "1:41","A fish-girl escapes the ocean and befriends a young boy, causing magical chaos on land.");

INSERT INTO favorites (usr_id, movie_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10),
    (2, 1),
    (2, 2),
    (3, 3),
    (3,4),
    (4,4);