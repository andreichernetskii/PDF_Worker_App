public enum HelpText {
    OPEN_FILES_TIP("Press the \"Browse\" button to choose files. " + "\n" +
            "Then they will be displayed in the text box. " + "\n" +
            "To delete an element from the box, " + "\n" +
            "click on the element with the left mouse button."),
    SAVE_FILE_TIP("Press the \"Browse\" button to select" + "\n" +
            " a folder for file saving");

    public String label;

    private HelpText(String label) {
        this.label = label;
    }
}
