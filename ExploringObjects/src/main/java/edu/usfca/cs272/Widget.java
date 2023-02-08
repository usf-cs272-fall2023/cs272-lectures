package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This demo briefly illustrates some basic terminology for object-oriented
 * programming.
 *
 * <p>
 * This is an example of a <strong>class</strong> definition, which defines the
 * implementation of an instance of this object.
 *
 * @see Widget
 * @see WidgetDemo
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class Widget {
	/**
	 * The name of a widget.
	 *
	 * <p>
	 * This is an <strong>instance member</strong>. Each instance will have its own
	 * value. Initialization should occur in the constructor. (If you only use this
	 * value in a single method, it should be a local variable instead of an
	 * instance member.)
	 *
	 * <p>
	 * This is an example of a <strong>private member</strong>. Only this class can
	 * access this value. In general, you should make members private unless you
	 * have a reason otherwise. This helps the <strong>encapsulation</strong> of
	 * your code.
	 *
	 * <p>
	 * This is an example of an <strong>identifier</strong>, which is the name of
	 * something. It is how we access or refer to that element within the current
	 * scope.
	 *
	 * <p>
	 * This is an example of a <strong>declaration</strong>, which establishes an
	 * identifier and its associated attributes (such as the type).
	 */
	private String widgetName;

	/**
	 * The identifier for the widget.
	 *
	 * <p>
	 * This is a <strong>final member</strong>, meaning the member can only be
	 * <strong>defined</strong> once. For a primitive type, this means it can only
	 * be assigned a value once and is effectively constant.
	 */
	private final int widgetID;

	/**
	 * Number of initialized widgets.
	 *
	 * <p>
	 * This is a <strong>static member</strong> or <strong>class member</strong>.
	 * All instances of this class share this value. Static members should be
	 * <strong>defined</strong> (i.e. initialized) at <strong>declaration</strong>.
	 *
	 * <p>
	 * The <strong>definition</strong> of a member reserves storage (for data). The
	 * definition of a method provides an implementation for that method.
	 */
	private static int numWidgets = 0;

	/**
	 * The names of the initialized widgets.
	 *
	 * <p>
	 * This is a <strong>static final member</strong>. All instances of this class
	 * share this value. Since it is {@code final}, we must define/initialize at
	 * declaration. This is the only time a reference may be assigned to this
	 * member. If the member is a mutable object, this means the reference cannot be
	 * updated (but the mutable object remains mutable). More later.
	 *
	 * <p>
	 * This also demonstrates <strong>generic types</strong>. We have one
	 * {@link ArrayList} class, but we can create {@link ArrayList} objects that
	 * store different types of elements. (We do not have one {@link ArrayList}
	 * class specific to {@link String} objects, and another specific to
	 * {@link Integer} objects, and so on.) We specify the type with the {@code <>}
	 * generic notation. Generic types are a form of polymorphism ("many forms").
	 */
	private static final ArrayList<String> widgetNames = new ArrayList<>();

	/**
	 * Indicates whether to output debug messages.
	 *
	 * <p>
	 * This is a <strong>public</strong> static member. Any other class may directly
	 * modify this value. It is rare that making a member public is appropriate.
	 * This is one of the few cases where you could justify it, since this only
	 * changes the output and not the functionality of this class. Another case is
	 * for constants.
	 */
	public static boolean debug = false;

	/**
	 * Initializes a new widget instance with the supplied name.
	 *
	 * <p>
	 * This is a <strong>constructor</strong>. It is a special method that is called
	 * when an object/instance is created. It should initialize all of the
	 * <strong>instance</strong> members.
	 *
	 * @param widgetName the name of the widget
	 */
	public Widget(String widgetName) {
		/*
		 * THIS - The "this" keyword refers to the current instance. Here, we set this
		 * object's widget name to whatever was passed in to the constructor.
		 *
		 * Using this keyword helps indicate when code is referencing an instance member
		 * versus a local variable or static/class member. It is required when there is
		 * ambiguity caused by identical identifiers in different scopes. For example,
		 * here we have a local parameter widgetName and an instance member widgetName.
		 */
		this.widgetName = widgetName;

		/*
		 * FINAL - After this initialization, the value of widgetID may not change. (Try
		 * it!)
		 */
		this.widgetID = ++numWidgets;

		// Attempt redefine modify widgetID or widgetNames at this point!

		/*
		 * STATIC (ACCESS) - We access static members through the class name, to make it
		 * clear this changes the value for all instances of this class. It is not
		 * necessary WITHIN the class, however.
		 */
		Widget.widgetNames.add(widgetName);

		if (debug) {
			System.out.println("Created widget #" + widgetID + " named " + widgetName + ".");
		}
	}

	/**
	 * Initializes a new widget instance.
	 *
	 * <p>
	 * This is an example of <strong>overloading</strong>. You can provide methods
	 * with the same name, but different parameters. They should do the same thing!
	 * A good way to ensure this is to call the other overloaded method from this
	 * one. Method overloading is a form of polymorphism ("many forms").
	 *
	 * <p>
	 * This is a <strong>default constructor</strong>, which is a constructor that
	 * takes no parameters. It is often a good idea to create a default constructor.
	 */
	public Widget() {
		/*
		 * THIS - You can use the "this" keyword as a method too, which will call the
		 * appropriate constructor. This lets you reuse the initialization code you have
		 * written in a different constructor.
		 */
		this("Widget");
	}

	/**
	 * Returns the number of widgets initialized.
	 *
	 * <p>
	 * This is a <strong>static method</strong> or <strong>class method</strong> A
	 * static method may not access any instance members. The method itself does not
	 * require an instance to be created, and can be accessed through the class
	 * name. If the method does not access any instance members, make sure you make
	 * it static for clarity (and possibly a bit of speedup).
	 *
	 * <p>
	 * This is a <strong>getter</strong> or <strong>get method</strong>. Since this
	 * member is private, other classes cannot access this value (an example of
	 * <strong>encapsulation</strong>). We do not want other classes to be able to
	 * change this value, but it is okay for them to see the current value.
	 *
	 * @return the number of widgets
	 */
	public static int numWidgets() {
		return Widget.numWidgets;
	}

	/**
	 * Returns the widget names as a list.
	 *
	 * <p>
	 * This is an example of an <strong>unsafe get method</strong>. Since
	 * {@code widgetNames} is mutable, returning the reference is not safe. While
	 * the list reference cannot be modified, the contents of the list may be
	 * modified!
	 *
	 * @return the widget names
	 */
	public static List<String> getNamesUnsafe() {
		return widgetNames;
	}

	/**
	 * Returns the widget names as a list.
	 *
	 * <p>
	 * This is an example of a safe get method. Instead of returning the reference
	 * to our private mutable data directly, it is first made
	 * <strong>unmodifiable</strong>. While code in this class will still be able to
	 * mutate the data, code outside of this class will not.
	 *
	 * @return the widget names
	 */
	public static List<String> getNames() {
		return Collections.unmodifiableList(widgetNames);
	}

	/**
	 * Returns the widget name.
	 *
	 * <p>
	 * This is an example <strong>non-static</strong> or <strong>instance
	 * method</strong>, which can only be accessed through an actual instance. It is
	 * also a safe <strong>get</strong> method since <code>String</code> is an
	 * immutable type.
	 *
	 * @return the widget name
	 */
	public String getName() {
		return this.widgetName;
	}

	/**
	 * Returns whether the supplied name is valid.
	 *
	 * <p>
	 * This is an example of a <strong>private static helper method</strong>. Helper
	 * methods are not visible outside of the class and are meant to "help" the
	 * other public methods in this class.
	 *
	 * @param name the name to validate
	 * @return <code>true</code> if the name is valid, <code>false</code> otherwise
	 */
	private static boolean validName(String name) {
		return name != null && !name.strip().isBlank();
	}

	/**
	 * If the supplied name is valid, sets it as the widget name.
	 *
	 * <p>
	 * This is an example of a <strong>setter</strong> or <strong>set
	 * method</strong>. These methods allow you to validate unsafe input before
	 * changing the value. In this case, we make sure the name is not null or empty.
	 * If there is no validation to perform, ask whether the member in question
	 * really needs to be private. Often, these types of methods return a boolean to
	 * indicate whether the change was made.
	 *
	 * @param newName the new widget name
	 * @return {@code true} if the name was valid and set
	 */
	public boolean setName(String newName) {
		/*
		 * LOCAL VARIABLE - This variable only exists locally, inside this method. It
		 * isn't an "attribute" of this object so it does not make sense to make it a
		 * member.
		 *
		 * We use our helper method here, so that validation is always done
		 * consistently.
		 */
		boolean valid = validName(newName);

		if (valid) {
			this.widgetName = newName;
		}
		else if (debug) {
			/*
			 * THIS - Again, "this" refers to this instance. By default, whenever an Object
			 * is treated as a String, Java calls the toString() method automatically. So,
			 * the code below will call this.toString() automatically.
			 */
			System.out.println("Failed to change name for widget " + this);
		}

		return valid;
	}

	/**
	 * Returns the {@link String} representation of this widget.
	 *
	 * <p>
	 * This is an example of <strong>overriding</strong> and using the
	 * {@code @Override} annotation. It replaces, or overrides, the implementation
	 * of {@link Object#toString()}. More on this when we discuss inheritance.
	 */
	@Override
	public String toString() {
		return widgetName + " (" + widgetID + ")";
	}
}
