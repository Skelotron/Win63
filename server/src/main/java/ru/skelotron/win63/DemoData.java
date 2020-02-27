package ru.skelotron.win63;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.*;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemRepository;
import ru.skelotron.win63.repository.ItemSynchronizationRepository;
import ru.skelotron.win63.repository.SubscriptionRepository;
import ru.skelotron.win63.service.subscription.filter.field.ItemFilterField;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Log
public class DemoData {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ItemSynchronizationRepository itemSynchronizationRepository;

    @Autowired
    public DemoData(CategoryRepository categoryRepository, ItemRepository itemRepository, SubscriptionRepository subscriptionRepository, ItemSynchronizationRepository itemSynchronizationRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.itemSynchronizationRepository = itemSynchronizationRepository;
    }

    public void prepare() {
        log.info("Start Data Preparing");
        prepareCategories();
        prepareItems();
        prepareSubscriptions();
        prepareItemSynchronization();
        log.info("End Data Preparing");
    }

    public void prepareCategories() {
        CategoryEntity phones = new CategoryEntity("Телефоны", "/catalog/telefony/", "142");
        phones.getSubCategories().add( new CategoryEntity( "Рации", "/catalog/telefony/racii/", "145" ) );
        phones.getSubCategories().add( new CategoryEntity( "Аксессуары для телефонов", "/catalog/telefony/aksessuary-dlya-telefonov/", "146" ) );
        phones.getSubCategories().add( new CategoryEntity( "Умные часы и браслеты", "/catalog/telefony/umnye-chasy-i-braslety/", "578" ) );
        phones.getSubCategories().add( new CategoryEntity( "Зарядные устройства", "/catalog/telefony/zaryadnye-ustroistva/", "253" ) );
        phones.getSubCategories().add( new CategoryEntity( "Телефоны для дома и офиса", "/catalog/telefony/telefony-dlya-doma-i-ofisa/", "144" ) );
        phones.getSubCategories().add( new CategoryEntity( "Сотовые телефоны", "/catalog/telefony/sotovye-telefony/", "143" ) );
        for (CategoryEntity categoryEntity : phones.getSubCategories()) {
            categoryEntity.setParentCategory(phones);
        }
        categoryRepository.save( phones );

        CategoryEntity computers = new CategoryEntity("Компьютерная техника", "/catalog/kompyuternaya-tehnika/", "99");
        computers.getSubCategories().add( new CategoryEntity( "Ноутбуки", "/catalog/kompyuternaya-tehnika/noutbuki/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Устройства ввода", "/catalog/kompyuternaya-tehnika/ustroistva-vvoda/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Сетевые фильтры и удлинители", "/catalog/kompyuternaya-tehnika/setevye-filtry-i-udliniteli/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Системные блоки", "/catalog/kompyuternaya-tehnika/sistemnye-bloki/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Мониторы", "/catalog/kompyuternaya-tehnika/monitory/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Роутеры и модемы", "/catalog/kompyuternaya-tehnika/routery-i-modemy/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Электронные книги", "/catalog/kompyuternaya-tehnika/elektronnye-knigi/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Моноблоки", "/catalog/kompyuternaya-tehnika/monobloki/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Аксессуары для компьютеров", "/catalog/kompyuternaya-tehnika/aksessuary-dlya-kompyuterov/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Прочая оргтехника", "/catalog/kompyuternaya-tehnika/prochaya-orgtehnika/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Планшетные пк", "/catalog/kompyuternaya-tehnika/planshetnye-pk/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Жесткие диски", "/catalog/kompyuternaya-tehnika/jestkie-diski/" ) );
        computers.getSubCategories().add( new CategoryEntity( "Принтеры и МФУ", "/catalog/kompyuternaya-tehnika/printery-i-mfu/" ) );
        for (CategoryEntity categoryEntity : computers.getSubCategories()) {
            categoryEntity.setParentCategory(computers);
        }
        categoryRepository.save(computers);

        CategoryEntity tools = new CategoryEntity("Инструмент", "/catalog/instrument/", "84");
        tools.getSubCategories().add( new CategoryEntity( "Техника для сада", "/catalog/instrument/tehnika-dlya-sada/" ) );
        tools.getSubCategories().add( new CategoryEntity( "Электроинструмент", "/catalog/instrument/elektroinstrument/" ) );
        tools.getSubCategories().add( new CategoryEntity( "Бензоинструмент", "/catalog/instrument/benzoinstrument/" ) );
        tools.getSubCategories().add( new CategoryEntity( "Пневмоинструмент", "/catalog/instrument/pnevmoinstrument/" ) );
        tools.getSubCategories().add( new CategoryEntity( "Сварочное оборудование", "/catalog/instrument/svarochnoe-oborudovanie/" ) );
        tools.getSubCategories().add( new CategoryEntity( "Светотехника", "/catalog/instrument/svetotehnika/" ) );
        tools.getSubCategories().add( new CategoryEntity( "Ручной инструмент", "/catalog/instrument/ruchnoi-instrument/" ) );
        for (CategoryEntity categoryEntity : tools.getSubCategories()) {
            categoryEntity.setParentCategory(tools);
        }
        categoryRepository.save(tools);

        CategoryEntity games = new CategoryEntity("Игры и приставки", "/catalog/igry-i-pristavki/", "83");
        games.getSubCategories().add( new CategoryEntity( "Игры для приставок", "/catalog/igry-i-pristavki/igry-dlya-pristavok/" ) );
        games.getSubCategories().add( new CategoryEntity( "Аксессуары для игровых приставок", "/catalog/igry-i-pristavki/aksessuary-dlya-igrovyh-pristavok/" ) );
        games.getSubCategories().add( new CategoryEntity( "Настольные игры", "/catalog/igry-i-pristavki/nastolnye-igry/" ) );
        games.getSubCategories().add( new CategoryEntity( "Игровые приставки", "/catalog/igry-i-pristavki/igrovye-pristavki/" ) );
        for (CategoryEntity categoryEntity : games.getSubCategories()) {
            categoryEntity.setParentCategory(games);
        }
        categoryRepository.save(games);

        CategoryEntity home = new CategoryEntity("Товары для дома", "/catalog/tovary-dlya-doma/", "114");
        home.getSubCategories().add( new CategoryEntity( "Бритвы и машинки для стрижки", "/catalog/tovary-dlya-doma/britvy-i-mashinki-dlya-strijki/" ) );
        home.getSubCategories().add( new CategoryEntity( "Холодильники и морозильники", "/catalog/tovary-dlya-doma/holodilniki-i-morozilniki/" ) );
        home.getSubCategories().add( new CategoryEntity( "Микроволновые печи", "/catalog/tovary-dlya-doma/mikrovolnovye-pechi/" ) );
        home.getSubCategories().add( new CategoryEntity( "Стиральные машины", "/catalog/tovary-dlya-doma/stiralnye-mashiny/" ) );
        home.getSubCategories().add( new CategoryEntity( "Климатическое оборудование", "/catalog/tovary-dlya-doma/klimaticheskoe-oborudovanie/" ) );
        home.getSubCategories().add( new CategoryEntity( "Утюги и отпариватели", "/catalog/tovary-dlya-doma/utyugi-i-otparivateli/" ) );
        home.getSubCategories().add( new CategoryEntity( "Мультиварки", "/catalog/tovary-dlya-doma/multivarki/" ) );
        home.getSubCategories().add( new CategoryEntity( "Пылесосы", "/catalog/tovary-dlya-doma/pylesosy/" ) );
        home.getSubCategories().add( new CategoryEntity( "Швейные машины", "/catalog/tovary-dlya-doma/shveinye-mashiny/" ) );
        home.getSubCategories().add( new CategoryEntity( "Фены и плойки", "/catalog/tovary-dlya-doma/feny-i-ploiki/" ) );
        home.getSubCategories().add( new CategoryEntity( "Товары для кухни", "/catalog/tovary-dlya-doma/tovary-dlya-kuhni/" ) );
        home.getSubCategories().add( new CategoryEntity( "Весы", "/catalog/tovary-dlya-doma/vesy/" ) );
        home.getSubCategories().add( new CategoryEntity( "Мебель и интерьер", "/catalog/tovary-dlya-doma/mebel-i-interer/" ) );
        home.getSubCategories().add( new CategoryEntity( "Электрические плиты", "/catalog/tovary-dlya-doma/elektricheskie-plity/" ) );
        home.getSubCategories().add( new CategoryEntity( "Чайники и кофеварки", "/catalog/tovary-dlya-doma/chainiki-i-kofevarki/" ) );
        home.getSubCategories().add( new CategoryEntity( "Товары для красоты и здоровья", "/catalog/tovary-dlya-doma/tovary-dlya-krasoty-i-zdorovya/" ) );
        for (CategoryEntity categoryEntity : home.getSubCategories()) {
            categoryEntity.setParentCategory(home);
        }
        categoryRepository.save(home);

        CategoryEntity auto = new CategoryEntity("Авто", "/catalog/avto/", "132");
        auto.getSubCategories().add( new CategoryEntity( "Автомобильные сабвуферы", "/catalog/avto/avtomobilnye-sabvufery/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Автомобильные колонки", "/catalog/avto/avtomobilnye-kolonki/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Видеорегистраторы", "/catalog/avto/videoregistratory/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Радар-детекторы", "/catalog/avto/radardetektory/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Насосы и компрессоры", "/catalog/avto/nasosy-i-kompressory/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Автомобильные аксессуары и комплектующие", "/catalog/avto/avtomobilnye-aksessuary-i-komplektuyucshie/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Шины и диски", "/catalog/avto/shiny-i-diski/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Автомобильные усилители", "/catalog/avto/avtomobilnye-usiliteli/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Навигаторы", "/catalog/avto/navigatory/" ) );
        auto.getSubCategories().add( new CategoryEntity( "Автомагнитолы", "/catalog/avto/avtomagnitoly/" ) );
        for (CategoryEntity categoryEntity : auto.getSubCategories()) {
            categoryEntity.setParentCategory(auto);
        }
        categoryRepository.save(auto);

        CategoryEntity photo = new CategoryEntity("Фото и видеотехника", "/catalog/foto-i-videotehnika/", "125");
        photo.getSubCategories().add( new CategoryEntity( "Аксессуары для фото и видеокамер", "/catalog/foto-i-videotehnika/aksessuary-dlya-foto-i-videokamer/" ) );
        photo.getSubCategories().add( new CategoryEntity( "Фоторамки", "/catalog/foto-i-videotehnika/fotoramki/" ) );
        photo.getSubCategories().add( new CategoryEntity( "Фотоаппараты", "/catalog/foto-i-videotehnika/fotoapparaty/" ) );
        photo.getSubCategories().add( new CategoryEntity( "Видеокамеры", "/catalog/foto-i-videotehnika/videokamery/" ) );
        for (CategoryEntity categoryEntity : photo.getSubCategories()) {
            categoryEntity.setParentCategory(photo);
        }
        categoryRepository.save(photo);

        CategoryEntity personal = new CategoryEntity("Личные вещи", "/catalog/lichnye-vecshi/", "157");
        personal.getSubCategories().add( new CategoryEntity( "Верхняя одежда", "/catalog/lichnye-vecshi/verhnyaya-odejda/" ) );
        personal.getSubCategories().add( new CategoryEntity( "Кальяны и эл. сигареты", "/catalog/lichnye-vecshi/kalyany-i-el_-sigarety/" ) );
        personal.getSubCategories().add( new CategoryEntity( "Часы", "/catalog/lichnye-vecshi/chasy/" ) );
        personal.getSubCategories().add( new CategoryEntity( "Канцтовары", "/catalog/lichnye-vecshi/kanctovary/" ) );
        personal.getSubCategories().add( new CategoryEntity( "Кошельки и сумки", "/catalog/lichnye-vecshi/koshelki-i-sumki/" ) );
        for (CategoryEntity categoryEntity : personal.getSubCategories()) {
            categoryEntity.setParentCategory(personal);
        }
        categoryRepository.save(personal);

        CategoryEntity hobby = new CategoryEntity("Хобби и отдых", "/catalog/hobbi-i-otdyh/", "148");
        hobby.getSubCategories().add( new CategoryEntity( "Водный инвентарь", "/catalog/hobbi-i-otdyh/vodnyi-inventar/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Роликовые и ледовые коньки", "/catalog/hobbi-i-otdyh/rolikovye-i-ledovye-konki/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Велосипеды и самокаты", "/catalog/hobbi-i-otdyh/velosipedy-i-samokaty/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Спортивный инвентарь", "/catalog/hobbi-i-otdyh/sportivnyi-inventar/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Товары для животных", "/catalog/hobbi-i-otdyh/tovary-dlya-jivotnyh/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Тренажеры", "/catalog/hobbi-i-otdyh/trenajery/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Пневматика и электрошокеры", "/catalog/hobbi-i-otdyh/pnevmatika-i-elektroshokery/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Туризм", "/catalog/hobbi-i-otdyh/turizm/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Квадрокоптеры", "/catalog/hobbi-i-otdyh/kvadrokoptery/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Коллекционирование", "/catalog/hobbi-i-otdyh/kollekcionirovanie/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Музыкальные инструменты", "/catalog/hobbi-i-otdyh/muzykalnye-instrumenty/" ) );
        hobby.getSubCategories().add( new CategoryEntity( "Тату-машинки", "/catalog/hobbi-i-otdyh/tatumashinki/" ) );
        for (CategoryEntity categoryEntity : hobby.getSubCategories()) {
            categoryEntity.setParentCategory(hobby);
        }
        categoryRepository.save(hobby);

        CategoryEntity audio = new CategoryEntity("Аудиотехника", "/catalog/audiotehnika/", "91");
        audio.getSubCategories().add( new CategoryEntity( "Усилители", "/catalog/audiotehnika/usiliteli/" ) );
        audio.getSubCategories().add( new CategoryEntity( "Музыкальные центры", "/catalog/audiotehnika/muzykalnye-centry/" ) );
        audio.getSubCategories().add( new CategoryEntity( "Ресиверы", "/catalog/audiotehnika/resivery/" ) );
        audio.getSubCategories().add( new CategoryEntity( "MP3 плееры", "/catalog/audiotehnika/mp3-pleery/" ) );
        audio.getSubCategories().add( new CategoryEntity( "Домашние кинотеатры", "/catalog/audiotehnika/domashnie-kinoteatry/" ) );
        audio.getSubCategories().add( new CategoryEntity( "Колонки", "/catalog/audiotehnika/kolonki/" ) );
        for (CategoryEntity categoryEntity : audio.getSubCategories()) {
            categoryEntity.setParentCategory(audio);
        }
        categoryRepository.save(audio);

        CategoryEntity video = new CategoryEntity("ТВ и Видео", "/catalog/tv-i-video/", "108");
        video.getSubCategories().add( new CategoryEntity( "Видео", "/catalog/tv-i-video/video/" ) );
        video.getSubCategories().add( new CategoryEntity( "Цифровое телевидение", "/catalog/tv-i-video/cifrovoe-televidenie/" ) );
        video.getSubCategories().add( new CategoryEntity( "Телевизоры", "/catalog/tv-i-video/televizory/" ) );
        video.getSubCategories().add( new CategoryEntity( "Аксессуары для ТВ и видео", "/catalog/tv-i-video/aksessuary-dlya-tv-i-video/" ) );
        video.getSubCategories().add( new CategoryEntity( "Проекторы", "/catalog/tv-i-video/proektory/" ) );
        for (CategoryEntity categoryEntity : video.getSubCategories()) {
            categoryEntity.setParentCategory(video);
        }
        categoryRepository.save(video);

        CategoryEntity children = new CategoryEntity("Товары для детей", "/catalog/tovary-dlya-detei/", "147");
        children.getSubCategories().add( new CategoryEntity( "Детские автокресла", "/catalog/tovary-dlya-detei/detskie-avtokresla/" ) );
        children.getSubCategories().add( new CategoryEntity( "Детские игрушки", "/catalog/tovary-dlya-detei/detskie-igrushki/" ) );
        children.getSubCategories().add( new CategoryEntity( "Товары для младенцев", "/catalog/tovary-dlya-detei/tovary-dlya-mladencev/" ) );
        children.getSubCategories().add( new CategoryEntity( "Детские коляски", "/catalog/tovary-dlya-detei/detskie-kolyaski/" ) );
        children.getSubCategories().add( new CategoryEntity( "Радио и видео няни", "/catalog/tovary-dlya-detei/radio-i-video-nyani/" ) );
        for (CategoryEntity categoryEntity : children.getSubCategories()) {
            categoryEntity.setParentCategory(children);
        }
        categoryRepository.save(children);
    }

    @SuppressWarnings("MagicNumber")
    public void prepareItems() {
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/oppo-reno-z-4-128-2064000548151/", "Oppo Reno Z 4/128", BigDecimal.valueOf(14900.00), getDate("18/02/2020 18:53"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a70-2014900365044/", "Samsung Galaxy A70", BigDecimal.valueOf(15900.00), getDate("18/02/2020 18:53"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasy-apple-watch-5-serias-2069000483556/", "Часы Apple Watch 5 serias", BigDecimal.valueOf(24000.00), getDate("18/02/2020 18:53"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aalwqglfe.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/zaryadnye-ustroistva/dokstanciya-dlya-iphone-i-apple-watch-belkin-f8j183-2029900012416/", "Док-станция для IPhone и Apple Watch Belkin F8J183", BigDecimal.valueOf(4000.00), getDate("18/02/2020 18:53"), categoryRepository.findByExternalId( "253" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/bt-naushniki-tws-i31-2030200018008/", "BT Наушники TWS i31", BigDecimal.valueOf(800.00), getDate("18/10/2023 18:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/jinga-simple-f200n-2055000501857/", "Jinga Simple F200n", BigDecimal.valueOf(400.00), getDate("18/10/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-gts5610-2055000506265/", "Samsung GT-S5610", BigDecimal.valueOf(400.00), getDate("18/09/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--b1acdfjbh2acclca1a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-206-(rm872)-2011500394429/", "Nokia 206", BigDecimal.valueOf(390.00), getDate("18/09/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/smart-chasy-2020200173494/", "Смарт часы", BigDecimal.valueOf(750.00), getDate("18/09/2023 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasysmart-jet-kid-2020100155187/", "Часы-Смарт Jet Kid", BigDecimal.valueOf(800.00), getDate("18/09/2023 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-100-2055000501093/", "Nokia 100", BigDecimal.valueOf(350.00), getDate("18/08/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/lg-leon-h324-2070000637863/", "LG Leon H324", BigDecimal.valueOf(2400.00), getDate("18/08/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j1-smj100f-2070000645158/", "Samsung Galaxy J1 SM-J100F", BigDecimal.valueOf(2000.00), getDate("18/07/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/fly-iq239-era-nano-2-2055000506357/", "Fly IQ239 ERA Nano 2", BigDecimal.valueOf(500.00), getDate("18/07/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-gio-gts5660-2070000633797/", "Samsung Galaxy Gio GT-S5660", BigDecimal.valueOf(800.00), getDate("18/06/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a10-2018800432981/", "Samsung Galaxy A10", BigDecimal.valueOf(5600.00), getDate("18/06/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-130-(rm1035)-2055000506531/", "Nokia 130", BigDecimal.valueOf(300.00), getDate("18/05/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/sony-xperia-m2-(d2303)-2055000506364/", "Sony Xperia M2 (D2303)", BigDecimal.valueOf(1400.00), getDate("18/04/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/smart-chasy-smart-watch-a1-2017000769491/", "Смарт часы Smart Watch A1", BigDecimal.valueOf(700.00), getDate("18/04/2023 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-n95-8gb-2070000644915/", "Nokia N95 8GB", BigDecimal.valueOf(2500.00), getDate("18/04/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-9x-2020100153602/", "Honor 9X 4/128GB", BigDecimal.valueOf(12000.00), getDate("18/02/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasy-smart-braslet-2013700400375/", "Часы смарт браслет", BigDecimal.valueOf(750.00), getDate("18/02/2023 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a70-2014900365044/", "Samsung Galaxy A70", BigDecimal.valueOf(15900.00), getDate("18/02/2020 18:53"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aalwqglfe.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/zaryadnye-ustroistva/dokstanciya-dlya-iphone-i-apple-watch-belkin-f8j183-2029900012416/", "Док-станция для IPhone и Apple Watch Belkin F8J183", BigDecimal.valueOf(4000.00), getDate("18/02/2020 18:53"), categoryRepository.findByExternalId( "253" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasy-apple-watch-5-serias-2069000483556/", "Часы Apple Watch 5 serias", BigDecimal.valueOf(24000.00), getDate("18/02/2020 18:53"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-11-pro-max-64-2016100237688/", "Apple iPhone 11 Pro Max 64GB", BigDecimal.valueOf(82000.00), getDate("18/02/2020 18:53"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/bt-naushniki-tws-i31-2030200018008/", "BT Наушники TWS i31", BigDecimal.valueOf(800.00), getDate("18/10/2023 18:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/jinga-simple-f200n-2055000501857/", "Jinga Simple F200n", BigDecimal.valueOf(400.00), getDate("18/10/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-gts5610-2055000506265/", "Samsung GT-S5610", BigDecimal.valueOf(400.00), getDate("18/09/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--b1acdfjbh2acclca1a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-206-(rm872)-2011500394429/", "Nokia 206", BigDecimal.valueOf(390.00), getDate("18/09/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/smart-chasy-2020200173494/", "Смарт часы", BigDecimal.valueOf(750.00), getDate("18/09/2023 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasysmart-jet-kid-2020100155187/", "Часы-Смарт Jet Kid", BigDecimal.valueOf(800.00), getDate("18/09/2023 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-100-2055000501093/", "Nokia 100", BigDecimal.valueOf(350.00), getDate("18/08/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/lg-leon-h324-2070000637863/", "LG Leon H324", BigDecimal.valueOf(2400.00), getDate("18/08/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j1-smj100f-2070000645158/", "Samsung Galaxy J1 SM-J100F", BigDecimal.valueOf(2000.00), getDate("18/07/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/fly-iq239-era-nano-2-2055000506357/", "Fly IQ239 ERA Nano 2", BigDecimal.valueOf(500.00), getDate("18/07/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-gio-gts5660-2070000633797/", "Samsung Galaxy Gio GT-S5660", BigDecimal.valueOf(800.00), getDate("18/06/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a10-2018800432981/", "Samsung Galaxy A10", BigDecimal.valueOf(5600.00), getDate("18/06/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-130-(rm1035)-2055000506531/", "Nokia 130", BigDecimal.valueOf(300.00), getDate("18/05/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/sony-xperia-m2-(d2303)-2055000506364/", "Sony Xperia M2 (D2303)", BigDecimal.valueOf(1400.00), getDate("18/04/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/smart-chasy-smart-watch-a1-2017000769491/", "Смарт часы Smart Watch A1", BigDecimal.valueOf(700.00), getDate("18/04/2023 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-n95-8gb-2070000644915/", "Nokia N95 8GB", BigDecimal.valueOf(2500.00), getDate("18/04/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-9x-2020100153602/", "Honor 9X 4/128GB", BigDecimal.valueOf(12000.00), getDate("18/02/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasy-smart-braslet-2013700400375/", "Часы смарт браслет", BigDecimal.valueOf(750.00), getDate("18/02/2023 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-7-3-32gb-2070000634602/", "Xiaomi Redmi 7 3/32gb", BigDecimal.valueOf(6500.00), getDate("18/02/2023 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-smb310e-2020100162291/", "Samsung SM-B310E", BigDecimal.valueOf(400.00), getDate("18/11/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/naushniki-prime-line-xbultra-2028100095540/", "Наушники PRIME LINE XB-Ultra", BigDecimal.valueOf(600.00), getDate("18/11/2022 18:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-8a-(jatlx1)-2013300664207/", "Honor 8A 32GB", BigDecimal.valueOf(5600.00), getDate("18/11/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--e1aner7ci.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasy-jetphone-sp1-2025400140888/", "Часы JetPhone SP1", BigDecimal.valueOf(500.00), getDate("18/11/2022 18:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/huawei-y5-lite-16gb-(dralx5)-2020100165964/", "Huawei Y5 Lite 16GB", BigDecimal.valueOf(3600.00), getDate("18/09/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/prestigio-muse-v3-2070000634039/", "Prestigio Muze V3 LTE", BigDecimal.valueOf(1600.00), getDate("18/09/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-10i-128-2070000633704/", "Honor 10i 128GB", BigDecimal.valueOf(9500.00), getDate("18/08/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-7a-2-16gb-2028100090606/", "Xiaomi Redmi 7A 2/16GB", BigDecimal.valueOf(4900.00), getDate("18/08/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a30-sma305f-32-2013300665136/", "Samsung Galaxy A30 SM-A305F 32", BigDecimal.valueOf(6200.00), getDate("18/07/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/inoi-101-2070000644526/", "INOI 101", BigDecimal.valueOf(450.00), getDate("18/07/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j2-core-smj260f-2028100094765/", "Samsung Galaxy J2 core SM-J260F", BigDecimal.valueOf(3500.00), getDate("18/06/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/bq-1846-one-power-2028100095014/", "BQ 1846 One Power", BigDecimal.valueOf(500.00), getDate("18/05/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7a-(dual22)-2030200014208/", "Honor 7A 16GB.", BigDecimal.valueOf(4500.00), getDate("18/04/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/huawei-y6-prime-(2018)-16gb-2070000637276/", "Huawei Y6 Prime (2018) 16GB", BigDecimal.valueOf(4500.00), getDate("18/04/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-6x-3-32gb-(blnl21)-2070000645592/", "Honor 6X 3/32GB", BigDecimal.valueOf(4500.00), getDate("18/04/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-xr-64-gb-2019300159101/", "Apple iPhone Xr 64 GB", BigDecimal.valueOf(30000.00), getDate("18/04/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-e1080-i-2028600036388/", "Samsung E1080 i", BigDecimal.valueOf(500.00), getDate("18/03/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7a-16gb_-(dual22)-2070000640443/", "Honor 7A 16GB.", BigDecimal.valueOf(3990.00), getDate("18/02/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-ace-4-lite-smg313h-2070000645646/", "Samsung Galaxy Ace 4 Lite SM-G313H", BigDecimal.valueOf(1200.00), getDate("18/02/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/inoi-5x-lite-2070000634749/", "INOI 5X Lite", BigDecimal.valueOf(2900.00), getDate("18/01/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/bq-bqs5020-strike-2055000505916/", "BQS-5020 Strike", BigDecimal.valueOf(1500.00), getDate("18/01/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-mi4-2-16gb-2055000500768/", "Xiaomi Mi4 2/16GB", BigDecimal.valueOf(3200.00), getDate("18/01/2022 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/huawei-y5-(2019)-2055000505749/", "Huawei Y5 (2019)", BigDecimal.valueOf(3600.00), getDate("18/12/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-star-advance-smg350e-2055000505909/", "Samsung Galaxy Star Advance SM-G350E", BigDecimal.valueOf(990.00), getDate("18/10/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/noa-next-se-2055000506012/", "NOA Next SE", BigDecimal.valueOf(1200.00), getDate("18/09/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/lg-g4c-h522y-2069000540594/", "LG G4c H522y", BigDecimal.valueOf(2900.00), getDate("18/09/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-note-5-3-32gb-2013700416277/", "Xiaomi Redmi Note 5 3/32Gb", BigDecimal.valueOf(6000.00), getDate("18/09/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7a-pro-16gb-(auml29)-2030200017964/", "Honor 7A Pro 16GB", BigDecimal.valueOf(5500.00), getDate("18/08/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-5s-16gb-(a1457)-2055000506227/", "Apple iPhone 5S 16GB", BigDecimal.valueOf(3600.00), getDate("18/08/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/prestigio-wize-n3-(psp3507)-2030200017865/", "Prestigio Wize N3", BigDecimal.valueOf(1200.00), getDate("18/07/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--b1acdfjbh2acclca1a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/naushniki-bt-aspor-2031200018746/", "Наушники BT Aspor", BigDecimal.valueOf(1290.00), getDate("18/07/2021 18:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/vertex-impress-luck-2030200014970/", "VERTEX Impress Luck", BigDecimal.valueOf(2300.00), getDate("18/04/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgfbsl1azdqr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/huawei-mate-20-lite-64gb-2012500284444/", "Huawei mate 20 lite 64GB", BigDecimal.valueOf(8590.00), getDate("18/03/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/lenovo-s580-2069000541379/", "Lenovo S580", BigDecimal.valueOf(1900.00), getDate("18/01/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-7-2-16gb-2055000501543/", "Xiaomi Redmi 7 2/16Gb", BigDecimal.valueOf(4900.00), getDate("18/01/2021 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-grand-prime-ve-smg531f-2069000549771/", "Samsung Galaxy Grand Prime VE SM-G531F", BigDecimal.valueOf(2500.00), getDate("18/12/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-7-2-16gb-2055000500737/", "Xiaomi Redmi 7 2/16Gb", BigDecimal.valueOf(5600.00), getDate("18/11/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aag1ciek.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-301-dual-sim-(rm839)-2024500087215/", "Nokia 301 Dual Sim", BigDecimal.valueOf(400.00), getDate("18/11/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-8x-4-64-2055000506449/", "Honor 8X 4/64", BigDecimal.valueOf(7900.00), getDate("18/10/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j1-(2016)-smj120f-ds-2069000549498/", "Samsung Galaxy J1 (2016) SM-J120F/DS", BigDecimal.valueOf(2500.00), getDate("18/10/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j1-(2016)-smj120h-ds-2069000549467/", "Samsung Galaxy J1 (2016) SM-J120H/DS", BigDecimal.valueOf(2500.00), getDate("18/09/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7a-16gb_-(dual22)-2055000501758/", "Honor 7A 16GB.", BigDecimal.valueOf(3600.00), getDate("18/08/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j1-(2016)-smj120f-ds-2069000549504/", "Samsung Galaxy J1 (2016) SM-J120F/DS", BigDecimal.valueOf(2500.00), getDate("18/08/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j1-(2016)-smj120f-ds-2069000549474/", "Samsung Galaxy J1 (2016) SM-J120F/DS", BigDecimal.valueOf(2500.00), getDate("18/06/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/naushniki-jnl-tune-110-tws-2017300291074/", "Наушники JNL Tune 110 TWS", BigDecimal.valueOf(1900.00), getDate("18/06/2020 18:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80a1bd.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-xs-64-gb-2073000554611/", "Apple iPhone Xs 64 GB", BigDecimal.valueOf(39900.00), getDate("18/06/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-6s-64gb-2055000499697/", "Apple iPhone 6S 64GB", BigDecimal.valueOf(6200.00), getDate("18/05/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j1-(2016)-smj120h-ds-2069000549481/", "Samsung Galaxy J1 (2016) SM-J120H/DS", BigDecimal.valueOf(2500.00), getDate("18/05/2020 18:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/zaryadnye-ustroistva/power-bank-gerffins-2032000940084/", "Power Bank Gerffins", BigDecimal.valueOf(400.00), getDate("18/02/2020 18:00"), categoryRepository.findByExternalId( "253" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aag1ciek.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a30-3-32gb-2099000727367/", "Samsung Galaxy A30 3/32GB", BigDecimal.valueOf(5500.00), getDate("18/11/2024 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgfbsl1azdqr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/apple-airpods-1-2010200519606/", "Apple AirPods 1", BigDecimal.valueOf(5200.00), getDate("18/10/2024 17:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-s-ii-gti9100-2014600315813/", "Samsung Galaxy S II GT-I9100", BigDecimal.valueOf(1600.00), getDate("18/08/2024 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--90abblsferjb2bt1eyb.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/xiaomi-mi-band-2-2080000544498/", "Xiaomi mi band 2", BigDecimal.valueOf(590.00), getDate("18/07/2024 17:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aag1ciek.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-10i-128gb-2099000728784/", "Honor 10i 128GB", BigDecimal.valueOf(8900.00), getDate("18/06/2024 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/oysters-angarsk-2032000941364/", "Oysters Angarsk", BigDecimal.valueOf(400.00), getDate("18/06/2024 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--90abblsferjb2bt1eyb.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/naushniki-huawei-cmh1c-2080000533119/", "Наушники huawei cm-h1c", BigDecimal.valueOf(3200.00), getDate("18/04/2024 17:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80ajchmbncc8b.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/microsoft-lumia-640-(rm10771072)-2027400132535/", "Microsoft Lumia 640 (RM-1077\1072)", BigDecimal.valueOf(1200.00), getDate("18/03/2024 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/naushniki-air-pods-1-siries-a1523-2070000631113/", "Наушники air pods 1 siries a1523", BigDecimal.valueOf(4900.00), getDate("18/02/2024 17:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aalwqglfe.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j2-core-smj260f-2015100498549/", "Samsung Galaxy J2 core SM-J260F", BigDecimal.valueOf(1700.00), getDate("18/02/2024 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/airpods-1-2070000633742/", "AirPods 1", BigDecimal.valueOf(5000.00), getDate("18/02/2024 17:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-8-lite-4-32gb_-frdl09-2013300669561/", "Honor 8 4/32", BigDecimal.valueOf(1900.00), getDate("18/01/2024 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/airpods-2070000634084/", "AirPods", BigDecimal.valueOf(5500.00), getDate("18/12/2023 17:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a50-64-2070000645004/", "Samsung Galaxy A50 64", BigDecimal.valueOf(10500.00), getDate("18/11/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-note-9-128-(n960f)-2019300171660/", "Samsung Galaxy Note 9 128", BigDecimal.valueOf(25000.00), getDate("18/11/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j2-core-smj260f-2013300677917/", "Samsung Galaxy J2 core SM-J260F", BigDecimal.valueOf(3600.00), getDate("18/10/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-8-64gb-2070000633728/", "Apple iPhone 8 64GB", BigDecimal.valueOf(19900.00), getDate("18/10/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--90abblsferjb2bt1eyb.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/miband-4-2080000536097/", "Miband 4", BigDecimal.valueOf(1490.00), getDate("18/09/2023 17:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j4+-(2018)-3-32gb-(smj415fn)-2013300678136/", "Samsung Galaxy J4+ (2018) 3/32GB", BigDecimal.valueOf(5500.00), getDate("18/08/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/power-bank-30000-mah-2017300291838/", "Power Bank 30000 mAh", BigDecimal.valueOf(400.00), getDate("18/05/2023 17:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-xr-128gb-2070000646360/", "Apple iPhone Xr 128GB", BigDecimal.valueOf(34000.00), getDate("18/05/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7a-(dual22)-2013300665730/", "Honor 7A 16GB.", BigDecimal.valueOf(3200.00), getDate("18/03/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-6s-32gb-2055000506401/", "Apple iPhone 6S 32GB", BigDecimal.valueOf(8900.00), getDate("18/02/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-smb312e-2020100165827/", "Samsung SM-B312E", BigDecimal.valueOf(400.00), getDate("18/01/2023 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgga2aurlbcpcr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasy-samsung-galaxy-watch-2091000478140/", "Часы Samsung Galaxy Watch", BigDecimal.valueOf(8900.00), getDate("18/12/2022 17:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-note-4-4-64gb-2020100142620/", "Xiaomi Redmi Note 4 4/64GB", BigDecimal.valueOf(4200.00), getDate("18/12/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/inoi-245r-2019300171028/", "INOI 245R", BigDecimal.valueOf(800.00), getDate("18/12/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgga2aurlbcpcr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/umnye-chasy-i-braslety/chasy-samsung-galaxy-watch-2091000478157/", "Часы Samsung Galaxy Watch", BigDecimal.valueOf(8900.00), getDate("18/10/2022 17:00"), categoryRepository.findByExternalId( "578" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/no_1-gtx6800-2020100160211/", "NO.1 X6800", BigDecimal.valueOf(2500.00), getDate("18/10/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-5s-16gb-(a1457)-2019300171875/", "Apple iPhone 5S 16GB", BigDecimal.valueOf(2500.00), getDate("18/08/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j3-(2017)-2020100160150/", "Samsung Galaxy J3 (2017)", BigDecimal.valueOf(2700.00), getDate("18/08/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-7-32gb-2020100160310/", "Apple iPhone 7 32GB", BigDecimal.valueOf(10000.00), getDate("18/07/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/texet-tmd328-2017300293979/", "teXet TM-D328", BigDecimal.valueOf(600.00), getDate("18/05/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgfbsl1azdqr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/aceline-4275-2010200512287/", "Aceline 4275", BigDecimal.valueOf(490.00), getDate("18/05/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-9x-premium-2020100160617/", "Honor 9X Premium 6/128GB", BigDecimal.valueOf(12000.00), getDate("18/05/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-8-lite-16gb-2070000647251/", "Honor 8 Lite 16GB", BigDecimal.valueOf(6500.00), getDate("18/05/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/alcatel-one-touch-1016d-2017300300875/", "Alcatel One Touch 1016D", BigDecimal.valueOf(300.00), getDate("18/04/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/naushniki-bron-2023200092512/", "Наушники Bron", BigDecimal.valueOf(650.00), getDate("18/04/2022 17:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a40-2013300678594/", "Samsung Galaxy A40", BigDecimal.valueOf(8500.00), getDate("18/03/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/fly-ff180-2017300300813/", "Fly FF180", BigDecimal.valueOf(250.00), getDate("18/03/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/meizu-m5c-16gb-(m710h)-2070000646865/", "Meizu M5c 16GB", BigDecimal.valueOf(3000.00), getDate("18/01/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-rex-90-gts5292-2017300294327/", "Samsung Rex 90 GT-S5292", BigDecimal.valueOf(500.00), getDate("18/01/2022 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a5-sma500f-2070000639478/", "Samsung Galaxy A5 SM-A500F", BigDecimal.valueOf(3900.00), getDate("18/12/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgfbsl1azdqr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-206-(rm872)-2010200511662/", "Nokia 206", BigDecimal.valueOf(350.00), getDate("18/11/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-11-pro-64gb-2070000647190/", "Apple iPhone 11 Pro 64GB", BigDecimal.valueOf(71900.00), getDate("18/11/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/vivo-y91-3-64gb-dual-sim-2070000646353/", "Vivo Y91 3/64GB Dual Sim", BigDecimal.valueOf(5900.00), getDate("18/10/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80agatlhjjbulh.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-3s-16gb-2027500109161/", "Xiaomi Redmi 3S 16GB", BigDecimal.valueOf(1900.00), getDate("18/08/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/huawei-mate-10-pro-6-128gb-2070000524910/", "Huawei Mate 10 Pro 6/128GB", BigDecimal.valueOf(12900.00), getDate("18/08/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/itel-mobile-it2163r-2070000638020/", "Itel mobile it2163r", BigDecimal.valueOf(450.00), getDate("18/08/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgfbsl1azdqr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/fly-banana-2010200512089/", "Fly Banana", BigDecimal.valueOf(590.00), getDate("18/06/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgfbsl1azdqr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/fly-fs408-stratus-8-2010200518692/", "Fly FS408 Stratus 8", BigDecimal.valueOf(1500.00), getDate("18/01/2021 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-5s-16gb-(a1457)-2013300665266/", "Apple iPhone 5S 16GB", BigDecimal.valueOf(3200.00), getDate("18/12/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-7-plus-128gb-2070000646513/", "Apple iPhone 7 Plus 128GB", BigDecimal.valueOf(15900.00), getDate("18/11/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-core-prime-ve-smg361h-ds-2070000636378/", "Samsung Core Prime VE SM-G361H/DS", BigDecimal.valueOf(2400.00), getDate("18/11/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-10i-128-2030000890712/", "Honor 10i 128GB", BigDecimal.valueOf(9500.00), getDate("18/11/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-5-plus-3-32gb-2013700412590/", "Xiaomi Redmi 5 Plus 3/32GB", BigDecimal.valueOf(5000.00), getDate("18/11/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7x-64gb-(bndl21)-2017300301476/", "Honor 7X 64GB", BigDecimal.valueOf(6200.00), getDate("18/10/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/inoi-2-lite-2019-2070000646919/", "INOI 2 Lite 2019", BigDecimal.valueOf(2400.00), getDate("18/10/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-s3-neo-gti9301i-2030000896868/", "Samsung Galaxy S3 Neo GT-I9301I", BigDecimal.valueOf(1900.00), getDate("18/09/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-105-(rm908)-2019000612739/", "Nokia 105 (RM-908)", BigDecimal.valueOf(490.00), getDate("18/08/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a70-2070000576414/", "Samsung Galaxy A70", BigDecimal.valueOf(14900.00), getDate("18/08/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-8-dual-sim-(ta1004)-2070000635548/", "Nokia 8 Dual sim (TA-1004)", BigDecimal.valueOf(5500.00), getDate("18/08/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a50-64-2030000884438/", "Samsung Galaxy A50 64", BigDecimal.valueOf(9200.00), getDate("18/08/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a5-(2017)-sma520f-2017300301490/", "Samsung Galaxy A5 (2017) SM-A520F", BigDecimal.valueOf(3900.00), getDate("18/07/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/bq-5009l-trend-2030000881741/", "BQ -5009L Trend", BigDecimal.valueOf(2500.00), getDate("18/06/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/huawei-y7-(2019)-2070000636064/", "Huawei Y7 (2019) 32GB", BigDecimal.valueOf(6900.00), getDate("18/06/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/asus-zenfone-3-max-zc520tl-16gb-(x008d)-2030000878833/", "ASUS ZenFone 3 Max ‏ZC520TL 16GB", BigDecimal.valueOf(3900.00), getDate("18/05/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-3-(ta1032)-2070000646490/", "Nokia 3 (TA-1032)", BigDecimal.valueOf(2990.00), getDate("18/05/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/mts-252-2030000890187/", "МТС 252", BigDecimal.valueOf(300.00), getDate("18/03/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7a-(dual22)-2070000636194/", "Honor 7A 16GB.", BigDecimal.valueOf(4500.00), getDate("18/03/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/oysters-ufa-2020000757320/", "Oysters Ufa", BigDecimal.valueOf(150.00), getDate("18/03/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/meizu-m5-note-32gb-2020100161805/", "Meizu M5 Note 32GB", BigDecimal.valueOf(4000.00), getDate("18/02/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-mi-6-6-64gb-2013700422889/", "Xiaomi Mi 6 6/64GB", BigDecimal.valueOf(8000.00), getDate("18/01/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aauks4g.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/nokia-3-(ta1032)-2030000884063/", "Nokia 3 (TA-1032)", BigDecimal.valueOf(3500.00), getDate("18/01/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/alcatel-1-(5033d)-(5033d)-2017300301254/", "Alcatel 1 (5033D)", BigDecimal.valueOf(2200.00), getDate("18/01/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgfbsl1azdqr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7a-pro-16gb-(auml29)-2010200518654/", "Honor 7A Pro 16GB", BigDecimal.valueOf(2290.00), getDate("18/01/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-j7-neo-smj701f-ds-2020100159727/", "Samsung Galaxy J7 Neo SM-J701F/DS", BigDecimal.valueOf(4500.00), getDate("18/01/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-6a-2-16gb-2070000634930/", "Xiaomi 6A 2/16GB", BigDecimal.valueOf(4200.00), getDate("18/01/2020 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-se-32gb-2070000646667/", "Apple iPhone SE 32GB", BigDecimal.valueOf(5900.00), getDate("18/12/2019 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-7a-16gb_-(dual22)-2019300171622/", "Honor 7A 16GB.", BigDecimal.valueOf(4000.00), getDate("18/12/2019 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/inoi-239-2020100166336/", "INOI 239", BigDecimal.valueOf(350.00), getDate("18/12/2019 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/xiaomi-redmi-7a-2-16gb-2017300301414/", "Xiaomi Redmi 7A 2/16GB", BigDecimal.valueOf(4900.00), getDate("18/12/2019 17:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/htc-desire-820g-dual-sim-2070000646018/", "HTC Desire 820G Dual Sim", BigDecimal.valueOf(2900.00), getDate("18/11/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80aaa0cvac.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/digma-a-105-2020100166206/", "Digma LINX A105 2G", BigDecimal.valueOf(300.00), getDate("18/11/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--h1afipca2eya.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-s500-2009000745674/", "Samsung SGH-S500", BigDecimal.valueOf(150.00), getDate("18/10/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-7-32gb-vosstanovlennyi-2070000638754/", "Apple iPhone 7 32GB восстановленный", BigDecimal.valueOf(10900.00), getDate("18/09/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/zte-blade-x3-(t620)-2017300301582/", "ZTE Blade X3", BigDecimal.valueOf(2600.00), getDate("18/09/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgga2aurlbcpcr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/karta-pamyati-microsd-samsung-64gb-2091000507208/", "Карта памяти microsd samsung 64gb", BigDecimal.valueOf(690.00), getDate("18/08/2024 16:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/lg-k8-k350e-lte-2017100420315/", "LG K8 K350E", BigDecimal.valueOf(3300.00), getDate("18/07/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/samsung-galaxy-a50-64-2070000635944/", "Samsung Galaxy A50 64", BigDecimal.valueOf(10500.00), getDate("18/07/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acmyge1e9a.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/apple-iphone-5s-16gb-(a1457)-2017300301698/", "Apple iPhone 5S 16GB", BigDecimal.valueOf(3200.00), getDate("18/07/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-8a-32gb-(jatlx1)-2070000637894/", "Honor 8A 32GB", BigDecimal.valueOf(5500.00), getDate("18/07/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80acgga2aurlbcpcr.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/aksessuary-dlya-telefonov/fleshka-samsung-micro-sd64-2026800073097/", "Флешка Samsung Micro SD64", BigDecimal.valueOf(690.00), getDate("18/05/2024 16:00"), categoryRepository.findByExternalId( "146" ), Collections.emptySet() ) );
        itemRepository.save( new Item( "https://xn--80adxhks.xn---63-5cdesg4ei.xn--p1ai/catalog/telefony/sotovye-telefony/honor-8s-32gb-2070000637030/", "Honor 8S 32GB", BigDecimal.valueOf(4900.00), getDate("18/05/2024 16:00"), categoryRepository.findByExternalId( "143" ), Collections.emptySet() ) );
    }

    private void prepareSubscriptions() {
        subscriptionRepository.save(
                new Subscription(
                        categoryRepository.findByExternalId( "143" ),
                        new HashSet<>(Arrays.asList(
                                new EmailNotified( "skelotron@gmail.com", "New Item: <ItemName>", "New Item: <ItemName>\n <ItemDescription>\n Price: <ItemCost>", new HashSet<>( Arrays.asList( new Filter( FilterRelationType.GREATER, Item.ENTITY_NAME, ItemFilterField.PRICE.name(), "500.0" ) ) ) ),
                                new EmailNotified( "khaliulin.r.r@gmail.com", "New Item: <ItemName>", "New Item: <ItemName>\n <ItemDescription>\n Price: <ItemCost>", new HashSet<>( Arrays.asList( new Filter( FilterRelationType.GREATER, Item.ENTITY_NAME, ItemFilterField.PRICE.name(), "500.0" ) ) ) )
                        ) ) ) );
        subscriptionRepository.save( new Subscription( categoryRepository.findByExternalId( "84" ), new HashSet<>(Arrays.asList( new EmailNotified( "skelotron@gmail.com", "New Item: <ItemName>", "New Item: <ItemName>\n <ItemDescription>\n Price: <ItemCost>", new HashSet<>( Arrays.asList( new Filter( FilterRelationType.CONTAINS, Item.ENTITY_NAME, ItemFilterField.TITLE.name(), "New" ) ) ) ) ) ) ) );
    }

    private void prepareItemSynchronization() {
        Map<CategoryEntity, Set<Item>> groupedItems = new HashMap<>();
        for (Item item : itemRepository.findAll()) {
            groupedItems.computeIfAbsent(item.getCategory(), a -> new HashSet<>()).add(item);
        }

        for (Map.Entry<CategoryEntity, Set<Item>> entry : groupedItems.entrySet()) {
            ItemSynchronizationEntity entity = new ItemSynchronizationEntity();
            entity.setSyncDate(new Date());
            entity.setNewEntitiesCount( entry.getValue().size() );
            entity.setCategory( entry.getKey() );
            entity.setManual( true );
            itemSynchronizationRepository.save(entity);
        }
    }

    private Date getDate(String value) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
