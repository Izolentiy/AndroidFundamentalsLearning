package org.izolentiy.citiescam.list;

import org.izolentiy.citiescam.model.City;

/**
 * Интерфейс дял получения событий от списка городов.
 */
public interface CitySelectedListener {
    /**
     * Вызывается, когда указанный город был выбран в списке городов.
     */
    void onCitySelected(City city);
}
