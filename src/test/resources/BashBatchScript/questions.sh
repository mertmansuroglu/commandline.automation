echo "Hello, please introduce yourself. (Bash Script)"

echo -n "Your name: "
read -r name


echo -n "Favorite programming language? "
read -r lang


echo -n "Birth Year?"
read -r year


let age=2022-year

echo
echo "Answers:"
echo "1. Your name:$name"
echo "2. Your Favorite prog. lang. :$lang"
echo "3. Your age:$age"