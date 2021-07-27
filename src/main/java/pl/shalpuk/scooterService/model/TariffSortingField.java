package pl.shalpuk.scooterService.model;

public enum TariffSortingField {

    NAME("name"),
    DESCRIPTION("description"),
    COSTS("costs");


    private final String sortField;

    TariffSortingField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortField() {
        return sortField;
    }
}
