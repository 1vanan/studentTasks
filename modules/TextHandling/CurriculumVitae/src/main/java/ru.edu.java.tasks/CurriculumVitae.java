package ru.edu.java.tasks;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * GOALS<ul>
 * <li>To learn regular expressions (including groups), java.util.regex and java.lang.String.</li>
 * <li>To practice object-oriented programming and using Java Collections Framework.</li>
 * <li>To get used to throwing Exceptions in every case when it's required
 * but with minimal number of throw statements.</li>
 * </ul>
 * <p/>
 * TASK<br/>
 * Implement a class storing a single CV text (curriculum vitae, resume) with ability to update some CV parts.<br/>
 * Getter methods of the class return names and phone numbers taken from the CV;
 * update* methods change the last name and a phone number directly in the CV text.<br/>
 * The class can also hide and unhide some personal info in the CV.
 * <p/>
 * REQUIREMENTS<ul>
 * <li>An appropriate exception should be thrown in each method (excepts for {@link #setText(String)})
 * if the CV does not contain valid information required by the method.</li>
 * <li>To implement hiding/unhiding feature, an appropriate class from the Collections Framework
 * should be chosen.</li>
 * </ul>
 * <p/>
 * NOTES<ul>
 * <li>If you want to check you class in your main() you may copy a text of any real-life CV,
 * paste it to a plain text editor like Notepad in Windows, save it to a file and load the file in your main().</li>
 * </ul>
 *
 * @author Alexey Evdokimov
 */
public interface CurriculumVitae {
    /**
     * This is a regular expression to use in {@link #getPhones()}. It describes multiple phone number formats
     * like US, Russian and Ukrainian formats (for large cities); it does not match the UK and France formats.<br/>
     * The expression does not include a country code.
     * An area code (3 digits) and an extension (1 or more digits after "ext" or "ext.") are optional.
     * The number of digits in a number matching to the expression is not less than 7.
     * Examples of numbers matching the expression: "(916)125-4171", "495 926-93-47 ext.1846", "800 250 0890"
     */
    public static final String PHONE_PATTERN =
        "(\\(?([1-9][0-9]{2})\\)?[-. ]*)?([1-9][0-9]{2})[-. ]*(\\d{2})[-. ]*(\\d{2})(\\s*ext\\.?\\s*([0-9]+))?";

    /**
     * Sets the text (the main field of the CV).<br/>
     * Implementation note: The text should not be analyzed in this method.<br/>
     *
     * @param text CV text
     */
    void setText(String text);

    /**
     * It is recommended to call this method in all the other methods of you class.
     *
     * @return The current text of the CV (may be changed by update* methods).
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    String getText() throws IllegalStateException;

    /**
     * Returns a list of phones in the same order they occur in the CV.<br/>
     * Implementation note: Use {@link #PHONE_PATTERN} to find phones;
     * use groups of that regular expression to get area code and extension from the matches found;
     * if the area code or extension does not exist there, the Phone must store negative value of it.
     *
     * @return A list that can't be null but may be empty (if no one phone is found).
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     * @see Phone
     */
    List<Phone> getPhones() throws IllegalStateException;

    /**
     * Returns the full name
     * i.e. the FIRST part of the CV text that satisfies the following criteria:
     * <ol>
     * <li>full name consists of 2 or 3 words separated with a space (' ');</li>
     * <li>each word has 2 or more characters;</li>
     * <li>the first character of the word is upper-case Latin letter (alphabetic character);</li>
     * <li>the last character of the word is either '.' or lower-case Latin letter;</li>
     * <li>non-first and non-last characters of the word can be lower-case Latin letter only.</li>
     * </ol>
     *
     * @return The full name (is exactly equal to its value in the CV text)
     * @throws NoSuchElementException If the CV does not contain a full name that satisfies the criteria.
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    String getFullName() throws NoSuchElementException, IllegalStateException;

    /**
     * Returns the first name from the CV (the first word of {@link #getFullName()}).
     *
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    String getFirstName() throws NoSuchElementException, IllegalStateException;

    /**
     * Returns the middle name from the CV (the second word of {@link #getFullName()})
     * or null if the full name has two words only.
     *
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    String getMiddleName() throws NoSuchElementException, IllegalStateException;

    /**
     * Returns the last name from the CV (the last word of {@link #getFullName()}).
     *
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    String getLastName() throws NoSuchElementException, IllegalStateException;

    /**
     * Replaces the lastName to <code>newLastName</code> in the CV text.
     *
     * @param newLastName Can't be null
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     * @see #getLastName()
     */
    void updateLastName(String newLastName) throws NoSuchElementException, IllegalStateException;

    /**
     * Replaces the <code>oldPhone.getNumber()</code> to <code>newPhone.getNumber()</code>
     * in the CV text.
     * Implementation note: using regex here leads to more code than using non-regex methods of {@link String}
     * (or maybe than using non-regex method of {@link String} and a method of {@link StringBuilder}).
     *
     * @param oldPhone Can't be null
     * @param newPhone Can't be null
     * @throws IllegalArgumentException If the CV does not contain a text equal to <code>oldPhone.getNumber()</code>.
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    void updatePhone(Phone oldPhone, Phone newPhone) throws IllegalArgumentException, IllegalStateException;

    /**
     * Finds the <code>piece</code> in the CV text and replaces <code>piece</code>'s characters
     * with 'X' character, excluding the following separating characters: ' ', '.' and '@'.
     * The number of 'X' characters is equal to a number of the replaced characters.<br/>
     * For example: "John A. Smith" is replaced by "XXXX X. XXXXX", "john@hp.com" - by "XXXX@XX.XXX".<br/>
     * This change can be undone by {@link #unhideAll()}.
     *
     * @param piece Can't be null
     * @throws IllegalArgumentException If the CV does not contain a text equal to <code>piece</code>.
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    void hide(String piece) throws IllegalArgumentException, IllegalStateException;

    /**
     * Finds the <code>phone</code> in the CV text
     * and replaces all DIGITS of the <code>phone</code> with 'X' character.<br/>
     * For example: "(123)456 7890" is replaced by "(XXX)XXX XXXX".<br/>
     * This change can be undone by {@link #unhideAll()}.
     *
     * @param phone Can't be null
     * @throws IllegalArgumentException If the CV does not contain a text equal to <code>phone</code>.
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    void hidePhone(String phone) throws IllegalArgumentException, IllegalStateException;

    /**
     * Undoes all changes made by {@link #hide(String)} and {@link #hidePhone(String)} methods
     * i.e. replaces 'X' pieces of the current CV text ("hidden" pieces inserted by those methods)
     * with appropriate pieces of the original CV text.<br/>
     * Note: there can't be 2 (or more) equal hidden pieces (equal pieces with 'X') in the CV text.<br/>
     * Implementation note: original and hidden pieces should be stored in some collection.
     * Moreover, the collection should be cleared on {@link #setText(String)}.
     *
     * @return The number of replacements made in the CV during unhiding
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    int unhideAll() throws IllegalStateException;

    /**
     * This class stores information about a phone number.<br/>
     * In addition to the full number (String) it can store two optional fields
     * (extracted from the full number and converted to int): areaCode and extension.
     *
     * @see CurriculumVitae#PHONE_PATTERN
     */
    public static class Phone {
        private String number;
        private int areaCode;
        private int extension;

        public Phone(String number) {
            this.number = number;
            this.areaCode = -1;
            this.extension = -1;
        }

        public Phone(String number, int areaCode, int extension) {
            this.number = number;
            this.areaCode = areaCode;
            this.extension = extension;
        }

        /**
         * @return The whole number
         */
        public final String getNumber() {
            return number;
        }

        /**
         * @return areaCode (or a negative value if the area code is not available)
         */
        public final int getAreaCode() {
            return areaCode;
        }

        /**
         * @return extension (or a negative value if the extension is not available)
         */
        public final int getExtension() {
            return extension;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Phone))
                return false;
            Phone p = (Phone)obj;
            if (getNumber() == null)//null is considered to be equal to null
                return p.getNumber() == null;
            if (!getNumber().equals(p.getNumber()))
                return false;
            if (!equalsOptional(getAreaCode(), p.getAreaCode()))
                return false;
            if (!equalsOptional(getExtension(), p.getExtension()))
                return false;
            return true;
        }

        private boolean equalsOptional(int v1, int v2) {
            if (v1 < 0)
                return v2 < 0;
            else
                return v1 == v2;
        }

        private void addMarker(StringBuilder sb, int value, boolean fromStart) {
            String s = "";
            if (value < 0) {
                s = fromStart ? "{NO CODE}" : "{NO EXT}";
            }
            else {
                String sValue = Integer.toString(value);
                int j = fromStart ? sb.indexOf(sValue) : sb.lastIndexOf(sValue);
                if (j < 0 || (fromStart && j > 1) || (!fromStart && j < sb.length() - sValue.length())) {
                    s = "{WRONG:" + sValue + "}";
                }
            }
            if (fromStart)
                sb.insert(0, s);
            else
                sb.append(s);
        }

        /**
         * This String representation of the phone
         * adds "{NO CODE}" before the {@link #number} if the {@link #areaCode} is negative and
         * adds "{NO EXT}" after the {@link #number} if the {@link #extension} is negative.
         * If the areaCode / extension does not match the number, "WRONG" is added before / after result.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(number);
            addMarker(sb, areaCode, true);
            addMarker(sb, extension, false);
            return sb.toString();
        }
    }//end of class Phone

}


