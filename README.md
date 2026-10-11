
## Javadoc

Dokumentasi API ada di folder `docs/` (buka `docs/index.html`). Untuk membuat ulang:

```bash
javadoc -encoding UTF-8 -d docs -sourcepath src -subpackages antrian:model:poli:rumahsakit src/Main.java src/DemoSkenario.java
```

Di IntelliJ IDEA: *Tools > Generate JavaDoc*.

Skenario uji tambahan (Singleton, antrian kosong, urutan FIFO): `java -cp out DemoSkenario`.
