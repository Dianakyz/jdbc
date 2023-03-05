import java.util.Objects;

public class City {
    private int city_id;
    private String name_city;

    public City(int city_id, String name_city) {
        this.city_id = city_id;
        this.name_city = name_city;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName_city() {
        return name_city;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return city_id == city.city_id && Objects.equals(name_city, city.name_city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city_id, name_city);
    }

    @Override
    public String toString() {
        return "City{" +
                "city_id=" + city_id +
                ", name_city='" + name_city + '\'' +
                '}';
    }
}
