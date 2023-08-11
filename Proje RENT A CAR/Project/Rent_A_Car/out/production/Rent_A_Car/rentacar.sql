-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 30 Haz 2023, 16:16:03
-- Sunucu sürümü: 10.4.28-MariaDB
-- PHP Sürümü: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `rentacar`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `araclar`
--

CREATE TABLE `araclar` (
  `id` int(255) NOT NULL,
  `Marka` varchar(255) NOT NULL,
  `Model` varchar(255) NOT NULL,
  `Tutar` varchar(10000) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `araclar`
--

INSERT INTO `araclar` (`id`, `Marka`, `Model`, `Tutar`) VALUES
(1, 'Skoda', 'Fabia', '1000'),
(2, 'Bmw ', '320i', '2000'),
(3, 'Mercedes', 'c180', '2000');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `musteri`
--

CREATE TABLE `musteri` (
  `id` int(255) NOT NULL,
  `MusteriAdi` varchar(255) NOT NULL,
  `MusteriSoyadi` varchar(255) NOT NULL,
  `MusteriMail` varchar(255) NOT NULL,
  `DogumTarihi` date NOT NULL,
  `KiralanacakTarih` date NOT NULL,
  `TeslimTarihi` date NOT NULL,
  `Tutar` mediumtext NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `musteri`
--

INSERT INTO `musteri` (`id`, `MusteriAdi`, `MusteriSoyadi`, `MusteriMail`, `DogumTarihi`, `KiralanacakTarih`, `TeslimTarihi`, `Tutar`) VALUES
(1, 'a', 'a', 'a', '2002-12-15', '2023-07-27', '2023-08-03', '7000.0'),
(2, 'b', 'b', 'b', '2002-12-10', '2023-07-27', '2023-08-03', '14000.0'),
(3, 'c', 'c', 'c', '2002-12-15', '2023-07-27', '2023-08-03', '14000.0');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `oturumacmasayfasi`
--

CREATE TABLE `oturumacmasayfasi` (
  `id` int(100) NOT NULL,
  `kullaniciadi` varchar(255) NOT NULL,
  `sifre` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `oturumacmasayfasi`
--

INSERT INTO `oturumacmasayfasi` (`id`, `kullaniciadi`, `sifre`) VALUES
(1, 'admin', '0000');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `araclar`
--
ALTER TABLE `araclar`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `musteri`
--
ALTER TABLE `musteri`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `oturumacmasayfasi`
--
ALTER TABLE `oturumacmasayfasi`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `araclar`
--
ALTER TABLE `araclar`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `musteri`
--
ALTER TABLE `musteri`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `oturumacmasayfasi`
--
ALTER TABLE `oturumacmasayfasi`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
