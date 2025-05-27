# Gogo Swing

Gogo Swing makes writing Swing applications easy.

# Why write a library for building Swing applications?

A long time ago, around the year 2006, I started writing a bookkeeping application that is now called Gogo Account.
At that time I had a Linux computer. I wanted to write a bookkeeping program that would work under Linux and also under Windows.
Since I had a lot of experience with Java and Swing I decided to use these technologies.

I discovered that while I was writing Gogo Account I was duplicating a lot of code. By applying the DRY principle
(Don't Repeat Yourself) I finally ended up with a library that lets me write Swing applications with less code.

# What does Gogo Swing offer?

- Date picker widget
- Models for a string, double, boolean and date that can be bound different types of widgets.
  Changes in the widgets are synchronized with the models and changes to the models are synchronized with the widgets.
- Dedicated panels for labeled input fields
- Views. A view represents a rectangular area inside a dialog or frame. A view typically implements a cohesive part of the user interface.
- Tabbed panes with a close button.
- Column definitions and several table models to make it easier to create tables with any type of data.
- Sortable tables.