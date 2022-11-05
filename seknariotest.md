LoginViewModelTest

1. Saat Login dan berhasil

- memastikan nilai observe pada livedata loginResponse berubah
- memastikan status Resource livedata loginRespons adalah sukses
- memastikan data nama livedata loginResponse sama dengan nama yang diterima dari repository

2. Saat Login dan gagal

- memastikan nilai observe pada livedata loginResponse berubah
- memastikan status Resource livedata loginRespons adalah error
- memastikan data message error livedata loginResponse sama dengan message error yang diterima dari
  repository

RegisterViewModelTest

1. Saat Register dan berhasil

- memastikan nilai observe pada livedata registerResponse berubah
- memastikan nilai observe pada livedata loginResponse berubah
- memastikan status Resource livedata registerResponse adalah sukses

2. Saat Register dan gagal

- memastikan nilai observe pada livedata registerResponse berubah
- memastikan status Resource livedata registerResponse adalah error
- memastikan data message error livedata registerResponse sama dengan message error yang diterima
  dari repository

DashboardViewModelTest

1. Saat mengambil data paging dengan kembalian sukses

- Memastikan data adapter tidal kosong
- memastikan adat yang di terima dari repository dan data adapter sama
- memastikan jumlah data sama
- memastikan id pada data pertama sama

2. Saat mengambil data untuk map dengan kembalian sukses

- memastikan nilai observe pada livedata StoriesMap berubah
- memastikan status Resource livedata StoriesMap adalah sukses
- memastikan ukuran data livedata StoriesMap sama dengan ukuran yang diterima dari repository

3. Saat mengambil data untuk map dengan kembalian gagal

- memastikan nilai observe pada livedata StoriesMap berubah
- memastikan status Resource livedata StoriesMap adalah error

AuthRepositoryTest

1. Saat Login dan berhasil

- memastikan response yang di terima sama dengan dataLogin yang diberikan dari service
    1. Saat Register dan berhasil
- memastikan response message yang di terima sama dengan message yang diberikan dari service

PagingMediatorTest

1. Saat memanggil paging distatus refresh dan data tersedia • Memastikan result yang di terima
   adalah sukses • Memastikan bahwa belum sampai pada akhir halaman


