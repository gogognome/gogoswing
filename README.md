# Gogo Swing
Gogo Swing makes writing Swing applications easy.

# Why write a library for building Swing applications?
A long time ago, around the year 2005, I started writing a bokokeeping application that is now called Gogo Account. At that time I had a Linux computer. I wanted to write a bookeeping program that would work under Linux and also under Windows. Since I had a log of experience with Java and Swing I decided to use these technologies.

I discovered that while I was writing Gogo Account I was duplicating a lot of code. By applying the DRY principal (Don't Repeat Yourself) I finally ended up with a library that lets me write Swing applications with less code.

#What does Gogo Swing offer?
- Date picker widget
- Column definitions to make it easier to create tables supporting different types of data
- Models for a string, double, boolean and date that can be bound different types of widgets. Changes in the widgets are synchronized with the models and changes to the models are synchronized with the widgets.
- Dedicated panels for labeled input fields
- Views. A view is a panel that can be closed.
