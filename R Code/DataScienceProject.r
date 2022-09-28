
#PREPROCESSING SECTION

# Read Dataset
weather.data<-read.csv("weatherAUS.csv")
w.data <- read.csv("weatherAUS.csv")

# Changed factored dates into normal dates
weather.data$Date<-as.Date(weather.data$Date, format = "%Y-%m-%d")

# Changed wind gust direction to a factor
weather.data$WindGustDir<-factor(weather.data$WindGustDir)

# Changed wind direction (at 9am and 3pm) to a factor
weather.data$WindDir9am<-factor(weather.data$WindDir9am)
weather.data$WindDir3pm<-factor(weather.data$WindDir3pm)

# Changed RainToday and RainTomorrow to a factor
weather.data$RainToday<-factor(weather.data$RainToday, labels = c("No", "Yes"))
weather.data$RainTomorrow<-factor(weather.data$RainTomorrow, labels = c("No", "Yes"))

# Searching for missing data
summary(weather.data$Date)
summary(weather.data$Location)
summary(weather.data$MinTemp)        # NA's = 1485
summary(weather.data$MaxTemp)        # NA's = 1261
summary(weather.data$Rainfall)       # NA's = 3261
summary(weather.data$Evaporation)    # NA's = 62790 <-- omit
summary(weather.data$Sunshine)       # NA's = 69835 <-- omit
summary(weather.data$WindGustDir)    # NA's = 10326
summary(weather.data$WindGustSpeed)  # NA's = 10326
summary(weather.data$WindDir9am)     # NA's = 10566
summary(weather.data$WindDir3pm)     # NA's = 4228
summary(weather.data$WindSpeed9am)   # NA's = 1767
summary(weather.data$WindSpeed3pm)   # NA's = 3062
summary(weather.data$Humidity9am)    # NA's = 2654
summary(weather.data$Humidity3pm)    # NA's = 4507
summary(weather.data$Pressure9am)    # NA's = 15065
summary(weather.data$Pressure3pm)    # NA's = 15028
summary(weather.data$Cloud9am)       # NA's = 55888 <-- omit
summary(weather.data$Cloud3pm)       # NA's = 59358 <-- omit
summary(weather.data$Temp9am)        # NA's = 1767
summary(weather.data$Temp3pm)        # NA's = 3609
summary(weather.data$RainToday)      # NA's = 3261
summary(weather.data$RainTomorrow)   # NA's = 3267


# Creating boxplot graphs to find outliers of numerical columns
# Winsorizing data --- sets data out of range upper bound if above and lower bound if below
# LowFen = 1st quartile - 1.5 * IQR(column)
# UpFen = 3rd quartile + 1.5 * IQR(column)
# IQR(column) = 3rd quartile - 1st quartile


# MinTemp
boxplot(weather.data$MinTemp)   # contains outliers
LowFen<- 7.60-1.5*(16.90-7.60)  # Lower Bound of MinTemp
UpFen<- 16.90+1.5*(16.90-7.60)  # Upper Bound of MinTemp
weather.data$MinTemp[weather.data$MinTemp<LowFen]<-LowFen
weather.data$MinTemp[weather.data$MinTemp>UpFen]<-UpFen


# MaxTemp
boxplot(weather.data$MaxTemp) # contains outliers
LowFen<- 17.90-1.5*(28.20-17.90)  # Lower Bound of MaxTemp
UpFen<- 28.20+1.5*(28.20-17.90)   # Upper Bound of MaxTemp
weather.data$MaxTemp[weather.data$MaxTemp<LowFen]<-LowFen
weather.data$MaxTemp[weather.data$MaxTemp>UpFen]<-UpFen


# Rainfall
boxplot(weather.data$Rainfall)
LowFen<- 0.000-1.5*(0.800-0.000)
UpFen<- 0.800+1.5*(0.800-0.000)
weather.data$Rainfall[weather.data$Rainfall<LowFen]<-LowFen
weather.data$Rainfall[weather.data$Rainfall>UpFen]<-UpFen


# WindGustSpeed
boxplot(weather.data$WindGustSpeed)
LowFen<- 31.00-1.5*(48.00-31.00)
UpFen<- 48.00+1.5*(48.00-31.00)
weather.data$WindGustSpeed[weather.data$WindGustSpeed<LowFen]<-LowFen
weather.data$WindGustSpeed[weather.data$WindGustSpeed>UpFen]<-UpFen


# WindSpeed9am
boxplot(weather.data$WindSpeed9am)
LowFen<- 7.00-1.5*(19.00-7.00)
UpFen<- 19.00+1.5*(19.00-7.00)
weather.data$WindSpeed9am[weather.data$WindSpeed9am<LowFen]<-LowFen
weather.data$WindSpeed9am[weather.data$WindSpeed9am>UpFen]<-UpFen


# WindSpeed3pm
boxplot(weather.data$WindSpeed3pm)
LowFen<- 13.00-1.5*(24.00-13.00)
UpFen<- 24.00+1.5*(24.00-13.00)
weather.data$WindSpeed3pm[weather.data$WindSpeed3pm<LowFen]<-LowFen
weather.data$WindSpeed3pm[weather.data$WindSpeed3pm>UpFen]<-UpFen


# Humidity9am
boxplot(weather.data$Humidity9am)
LowFen<- 57.00-1.5*(83.00-57.00)
UpFen<- 83.00+1.5*(83.00-57.00)
weather.data$Humidity9am[weather.data$Humidity9am<LowFen]<-LowFen
weather.data$Humidity9am[weather.data$Humidity9am>UpFen]<-UpFen


# Humidity3pm
boxplot(weather.data$Humidity3pm)  # No outliers


# Pressure9am
boxplot(weather.data$Pressure9am)
LowFen<- 1012.9-1.5*(1022.4-1012.9)
UpFen<- 1022.4+1.5*(1022.4-1012.9)
weather.data$Pressure9am[weather.data$Pressure9am<LowFen]<-LowFen
weather.data$Pressure9am[weather.data$Pressure9am>UpFen]<-UpFen


# Pressure3pm
boxplot(weather.data$Pressure3pm)
LowFen<- 1010.4-1.5*(1020.0-1010.4)
UpFen<- 1020.0+1.5*(1020.0-1010.4)
weather.data$Pressure3pm[weather.data$Pressure3pm<LowFen]<-LowFen
weather.data$Pressure3pm[weather.data$Pressure3pm>UpFen]<-UpFen


# Temp9am
boxplot(weather.data$Temp9am)
LowFen<- 12.30-1.5*(21.60-12.30)
UpFen<- 21.60+1.5*(21.60-12.30)
weather.data$Temp9am[weather.data$Temp9am<LowFen]<-LowFen
weather.data$Temp9am[weather.data$Temp9am>UpFen]<-UpFen


# Temp3pm
boxplot(weather.data$Temp3pm)
LowFen<- 16.60-1.5*(26.40-16.60)
UpFen<- 26.40+1.5*(26.40-16.60)
weather.data$Temp3pm[weather.data$Temp3pm<LowFen]<-LowFen
weather.data$Temp3pm[weather.data$Temp3pm>UpFen]<-UpFen


# Omitting columns with over 50,000 NA's
colnames(weather.data)
weather.data<-weather.data[c(-6,-7,-18,-19)]

# Further omitting columns that might overfit the model
# So I removed the 3pm data on only kept the 9am
colnames(weather.data)
#weather.data<-weather.data[c(-9, -11, -13, -15, -17)]

# Omitting non-numeric variables
colnames(weather.data)
weather.data<-weather.data[c(-1, -2, -6, -8, -9)]

# Omitting NA rows
weather.data <- na.omit(weather.data)
summary(weather.data)


# Checking correlation coefficient between two variables
weather.test <- weather.data[1:12]
result = cor(weather.test, method = "pearson", use = "complete.obs")
round(result,2)


# Normalizing data and adding factored variables back into data frame
normalize <- function(x) {
  return ((x - min(x)) / (max(x) - min(x))) 
} 
weather.norm<- as.data.frame(lapply(weather.data[1:12], normalize))

weather.norm$RainToday <- weather.data$RainToday
weather.norm$RainTomorrow <- weather.data$RainTomorrow

result = cor(weather.norm, method = "pearson", use = "complete.obs")
round(result,2)


# Writes file to a text document (as a .csv file)
# for my java program to work with if I need it
write.csv(weather.data, 'weatherAUS_Modified.csv')


# EXPORTING TRAINING AND TESTING DATASETS

# Creating my training and testing datasets as random samples
set.seed(2)
train.index <- sample(c(1:dim(weather.norm)[1]), dim(weather.norm)[1]*0.6)  
train.df <- weather.norm[train.index, ]
valid.df <- weather.norm[-train.index, ]

# re-randomizes testing data since "-train.index" ordered the dataframe
# in ascending order of the remaining indexes
rows <- sample(nrow(valid.df))
valid.df <- valid.df[rows,]


# Exporting training dataset as .csv file
write.csv(train.df, 'weather_trainingSet.csv')

# Exporting testing dataset as .csv file
write.csv(valid.df, 'weather_testingSet.csv')

