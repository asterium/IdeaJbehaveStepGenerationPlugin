package stepParser;

public enum StepType {
    GIVEN("given", "@Given"),
    WHEN("when", "@When"),
    THEN("then", "@Then");

    private String typeString;
    private String annotationDescription;

    public static StepType parse(String value){
        return StepType.valueOf(value.toUpperCase());
    }

    StepType(String typeString, String annotationDescription) {
        this.typeString = typeString;
        this.annotationDescription = annotationDescription;
    }

    public String getAnnotationText(){
        return annotationDescription;
    }
}
