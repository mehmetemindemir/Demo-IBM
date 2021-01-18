echo " Choose Project for deploy"
echo "1-)dev "
echo "2-)prod"
read result
if [ "$result" == "dev" ]; then
  mvn clean install -U
  docker build . -t dhub.mehmetemindemir.com/med/ibmdemo-api:T005
  docker push dhub.mehmetemindemir.com/med/ibmdemo-api:T005

elif [ "$result" == "prod" ]; then
  mvn clean install -U
  docker build . -t dhub.mehmetemindemir.com/med/ibmdemo-api:P001
  docker push dhub.mehmetemindemir.com/med/ibmdemo-api:P001
fi
