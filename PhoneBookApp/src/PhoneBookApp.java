import java.util.Scanner;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

// Contact Class
class Contact {
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber;
    }
}

// Linked List Node
class ContactNode {
    Contact contact;
    ContactNode next;

    public ContactNode(Contact contact) {
        this.contact = contact;
        this.next = null;
    }
}

// Linked List Phonebook
class LinkedListPhonebook {
    private ContactNode head;
    private Map<String, ContactGroup> contactGroups;

    public LinkedListPhonebook() {
        head = null;
        contactGroups = new HashMap<>();
    }

    // Insert Contact at the end of the list
    public void insertContact(String name, String phoneNumber) {
        Contact newContact = new Contact(name, phoneNumber);
        ContactNode newNode = new ContactNode(newContact);

        if (head == null) {
            head = newNode;
        } else {
            ContactNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        System.out.println("Contact added: " + name);
    }

    // Search Contact
    public Contact searchContact(String name) {
        ContactNode current = head;
        while (current != null) {
            if (current.contact.getName().equalsIgnoreCase(name)) {
                return current.contact;
            }
            current = current.next;
        }
        return null;
    }

    // Display all contacts
    public void displayAllContacts() {
        ContactNode current = head;
        if (current == null) {
            System.out.println("No contacts available.");
        } else {
            while (current != null) {
                System.out.println(current.contact);
                current = current.next;
            }
        }
    }

    // Delete contact by name
    public void deleteContact(String name) {
        if (head == null) {
            System.out.println("Contact not found.");
            return;
        }
        if (head.contact.getName().equalsIgnoreCase(name)) {
            head = head.next;
            System.out.println("Deleted contact: " + name);
            return;
        }

        ContactNode current = head;
        while (current.next != null) {
            if (current.next.contact.getName().equalsIgnoreCase(name)) {
                current.next = current.next.next;
                System.out.println("Deleted contact: " + name);
                return;
            }
            current = current.next;
        }
        System.out.println("Contact not found.");
    }

    // Edit contact
    public void editContact(String name, String newName, String newPhoneNumber) {
        ContactNode current = head;
        while (current != null) {
            if (current.contact.getName().equalsIgnoreCase(name)) {
                current.contact.setName(newName);
                current.contact.setPhoneNumber(newPhoneNumber);
                System.out.println("Contact updated: " + name);
                return;
            }
            current = current.next;
        }
        System.out.println("Contact not found.");
    }

    // Add contact to group
    public void addContactToGroup(String groupName, String name) {
        Contact contact = searchContact(name);
        if (contact != null) {
            if (!contactGroups.containsKey(groupName)) {
                contactGroups.put(groupName, new ContactGroup(groupName));
            }
            contactGroups.get(groupName).addContact(contact);
            System.out.println("Contact added to group " + groupName);
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Display contacts in group
    public void displayContactsInGroup(String groupName) {
        if (contactGroups.containsKey(groupName)) {
            contactGroups.get(groupName).displayContacts();
        } else {
            System.out.println("Group not found.");
        }
    }

    // Export contacts to file
    public void exportContactsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            ContactNode current = head;
            while (current != null) {
                writer.println(current.contact.getName() + "," + current.contact.getPhoneNumber());
                current = current.next;
            }
            System.out.println("Contacts exported to file " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error exporting contacts to file.");
        }
    }

    // Import contacts from file
    public void importContactsFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 2) {
                    insertContact(parts[0], parts[1]);
                }
            }
            System.out.println("Contacts imported from file " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error importing contacts from file.");
        }
    }
}

// Binary Search Tree Node
class BSTNode {
    Contact contact;
    BSTNode left, right;

    public BSTNode(Contact contact) {
        this.contact = contact;
        left = right = null;
    }
}

// Binary Search Tree Phonebook
class BSTPhonebook {
    private BSTNode root;
    private Map<String, ContactGroup> contactGroups;

    public BSTPhonebook() {
        root = null;
        contactGroups = new HashMap<>();
    }

    // Insert contact into the binary search tree
    public void insertContact(String name, String phoneNumber) {
        Contact contact = new Contact(name, phoneNumber);
        root = insertRec(root, contact);
        System.out.println("Contact added: " + name);
    }

    private BSTNode insertRec(BSTNode root, Contact contact) {
        if (root == null) {
            root = new BSTNode(contact);
            return root;
        }

        if (contact.getName().compareToIgnoreCase(root.contact.getName()) < 0) {
            root.left = insertRec(root.left, contact);
        } else if (contact.getName().compareToIgnoreCase(root.contact.getName()) > 0) {
            root.right = insertRec(root.right, contact);
        }
        return root;
    }

    // Search contact by name
    public Contact searchContact(String name) {
        return searchRec(root, name);
    }

    private Contact searchRec(BSTNode root, String name) {
        if (root == null || root.contact.getName().equalsIgnoreCase(name)) {
            return (root != null) ? root.contact : null;
        }

        if (name.compareToIgnoreCase(root.contact.getName()) < 0) {
            return searchRec(root.left, name);
        }

        return searchRec(root.right, name);
    }

    // In-order traversal (alphabetically sorted contacts)
    public void displayAllContacts() {
        if (root == null) {
            System.out.println("No contacts available.");
        } else {
            inOrder(root);
        }
    }

    private void inOrder(BSTNode root) {
        if (root != null) {
            inOrder(root.left);
            System.out.println(root.contact);
            inOrder(root.right);
        }
    }

    // Edit contact
    public void editContact(String name, String newName, String newPhoneNumber) {
        Contact contact = searchContact(name);
        if (contact != null) {
            contact.setName(newName);
            contact.setPhoneNumber(newPhoneNumber);
            System.out.println("Contact updated: " + name);
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Add contact to group
    public void addContactToGroup(String groupName, String name) {
        Contact contact = searchContact(name);
        if (contact != null) {
            if (!contactGroups.containsKey(groupName)) {
                contactGroups.put(groupName, new ContactGroup(groupName));
            }
            contactGroups.get(groupName).addContact(contact);
            System.out.println("Contact added to group " + groupName);
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Display contacts in group
    public void displayContactsInGroup(String groupName) {
        if (contactGroups.containsKey(groupName)) {
            contactGroups.get(groupName).displayContacts();
        } else {
            System.out.println("Group not found.");
        }
    }

    // Export contacts to file
    public void exportContactsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            exportRec(root, writer);
            System.out.println("Contacts exported to file " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error exporting contacts to file.");
        }
    }

    private void exportRec(BSTNode root, PrintWriter writer) {
        if (root != null) {
            exportRec(root.left, writer);
            writer.println(root.contact.getName() + "," + root.contact.getPhoneNumber());
            exportRec(root.right, writer);
        }
    }

    // Import contacts from file
    public void importContactsFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 2) {
                    insertContact(parts[0], parts[1]);
                }
            }
            System.out.println("Contacts imported from file " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error importing contacts from file.");
        }
    }
}

// Stack Phonebook
class StackPhonebook {
    private Stack<Contact> contactStack;

    public StackPhonebook() {
        contactStack = new Stack<>();
    }

    // Push a contact onto the stack
    public void insertContact(String name, String phoneNumber) {
        Contact contact = new Contact(name, phoneNumber);
        contactStack.push(contact);
        System.out.println("Contact added: " + name);
    }

    // View the most recently added contact
    public void viewMostRecentContact() {
        if (!contactStack.isEmpty()) {
            System.out.println("Most recent contact: " + contactStack.peek());
        } else {
            System.out.println("No contacts in the stack.");
        }
    }

    // Pop (remove) the most recent contact
    public void deleteMostRecentContact() {
        if (!contactStack.isEmpty()) {
            Contact removed = contactStack.pop();
            System.out.println("Removed contact: " + removed);
        } else {
            System.out.println("No contacts to remove.");
        }
    }

    // Display all contacts in stack (LIFO order)
    public void displayAllContacts() {
        if (contactStack.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("Contacts (LIFO): ");
            for (Contact contact : contactStack) {
                System.out.println(contact);
            }
        }
    }
}

// ContactGroup class
class ContactGroup {
    private String groupName;
    private List<Contact> contacts;

    public ContactGroup(String groupName) {
        this.groupName = groupName;
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void displayContacts() {
        System.out.println("Contacts in group " + groupName + ":");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}

// Main Program to Choose Data Structure
public class PhoneBookApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose Data Structure for Phonebook:");
        System.out.println("1. Linked List");
        System.out.println("2. Binary Search Tree");
        System.out.println("3. Stack");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                LinkedListPhonebook linkedListPhonebook = new LinkedListPhonebook();
                phonebookMenu(linkedListPhonebook, scanner);
                break;
            case 2:
                BSTPhonebook bstPhonebook = new BSTPhonebook();
                phonebookMenu(bstPhonebook, scanner);
                break;
            case 3:
                StackPhonebook stackPhonebook = new StackPhonebook();
                phonebookMenu(stackPhonebook, scanner);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    public static void phonebookMenu(Object phonebook, Scanner scanner) {
        while (true) {
            System.out.println("\nPhonebook Menu:");
            System.out.println("1. Insert Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Display All Contacts");
            System.out.println("4. Delete Contact");
            System.out.println("5. Edit Contact");
            System.out.println("6. Add Contact to Group");
            System.out.println("7. Display Contacts in Group");
            System.out.println("8. Export Contacts to File");
            System.out.println("9. Import Contacts from File");
            System.out.println("10. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    if (phonebook instanceof LinkedListPhonebook) {
                        ((LinkedListPhonebook) phonebook).insertContact(name, phoneNumber);
                    } else if (phonebook instanceof BSTPhonebook) {
                        ((BSTPhonebook) phonebook).insertContact(name, phoneNumber);
                    } else if (phonebook instanceof StackPhonebook) {
                        ((StackPhonebook) phonebook).insertContact(name, phoneNumber);
                    }
                    break;
                case 2:
                    System.out.print("Enter name to search: ");
                    String searchName = scanner.nextLine();
                    Contact foundContact = null;
                    if (phonebook instanceof LinkedListPhonebook) {
                        foundContact = ((LinkedListPhonebook) phonebook).searchContact(searchName);
                    } else if (phonebook instanceof BSTPhonebook) {
                        foundContact = ((BSTPhonebook) phonebook).searchContact(searchName);
                    }
                    if (foundContact != null) {
                        System.out.println("Contact found: " + foundContact);
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 3:
                    if (phonebook instanceof LinkedListPhonebook) {
                        ((LinkedListPhonebook) phonebook).displayAllContacts();
                    } else if (phonebook instanceof BSTPhonebook) {
                        ((BSTPhonebook) phonebook).displayAllContacts();
                    } else if (phonebook instanceof StackPhonebook) {
                        ((StackPhonebook) phonebook).displayAllContacts();
                    }
                    break;
                case 4:
                    System.out.print("Enter name to delete: ");
                    String deleteName = scanner.nextLine();
                    if (phonebook instanceof LinkedListPhonebook) {
                        ((LinkedListPhonebook) phonebook).deleteContact(deleteName);
                    } else if (phonebook instanceof StackPhonebook) {
                        ((StackPhonebook) phonebook).deleteMostRecentContact();
                    }
                    break;
                case 5:
                    System.out.print("Enter name to edit: ");
                    String editName = scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newEditName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newEditPhoneNumber = scanner.nextLine();
                    if (phonebook instanceof LinkedListPhonebook) {
                        ((LinkedListPhonebook) phonebook).editContact(editName, newEditName, newEditPhoneNumber);
                    } else if (phonebook instanceof BSTPhonebook) {
                        ((BSTPhonebook) phonebook).editContact(editName, newEditName, newEditPhoneNumber);
                    }
                    break;
                case 6:
                    System.out.print("Enter group name: ");
                    String groupName = scanner.nextLine();
                    System.out.print("Enter contact name to add: ");
                    String addContactName = scanner.nextLine();
                    if (phonebook instanceof LinkedListPhonebook) {
                        ((LinkedListPhonebook) phonebook).addContactToGroup(groupName, addContactName);
                    } else if (phonebook instanceof BSTPhonebook) {
                        ((BSTPhonebook) phonebook).addContactToGroup(groupName, addContactName);
                    }
                    break;
                case 7:
                    System.out.print("Enter group name to display: ");
                    String displayGroupName = scanner.nextLine();
                    if (phonebook instanceof LinkedListPhonebook) {
                        ((LinkedListPhonebook) phonebook).displayContactsInGroup(displayGroupName);
                    } else if (phonebook instanceof BSTPhonebook) {
                        ((BSTPhonebook) phonebook).displayContactsInGroup(displayGroupName);
                    }
                    break;
                case 8:
                    System.out.print("Enter filename to export: ");
                    String exportFilename = scanner.nextLine();
                    if (phonebook instanceof LinkedListPhonebook) {
                        ((LinkedListPhonebook) phonebook).exportContactsToFile(exportFilename);
                    } else if (phonebook instanceof BSTPhonebook) {
                        ((BSTPhonebook) phonebook).exportContactsToFile(exportFilename);
                    }
                    break;
                case 9:
                    System.out.print("Enter filename to import: ");
                    String importFilename = scanner.nextLine();
                    if (phonebook instanceof LinkedListPhonebook) {
                        ((LinkedListPhonebook) phonebook).importContactsFromFile(importFilename);
                    } else if (phonebook instanceof BSTPhonebook) {
                        ((BSTPhonebook) phonebook).importContactsFromFile(importFilename);
                    }
                    break;
                case 10:
                    System.out.println("Exiting phonebook...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}