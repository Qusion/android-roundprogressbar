<img src="banner.png" alt="demo" />
<p>
<img align="left" width="400" height="400" src="demo.gif" alt="demo" />
<p><h1 align="left">Android-RoundProgressbar</h1></p>
<p>Progress bar library with different styles and built-in animations</p>
</p>

</br></br></br></br></br></br></br></br></br></br></br></br>

## Demo
<img src="screen-1.png" alt="screen-1" width="800" height="600" align="center" />
<p>
<img src="var1.png" alt="screen-1" width="300" height="300" align="left" />
<img src="var2.png" alt="screen-1" width="300" height="300" align="left" />
<img src="var3.png" alt="screen-1" width="300" height="300" align="left" />
<img src="var4.png" alt="screen-1" width="300" height="300" align="left" />
</p>
</br></br></br></br></br></br></br></br></br></br></br></br></br>
</br></br></br></br></br></br></br></br></br></br></br></br></br>

## Usage
### Implementation
`dependencies {
    implementation 'com.github.Qusion:android-pin-dot-view:0.3.0'
}`

### Layout
```
<com.qusion.lib_roundprogressbar.RoundProgressBar
    android:id="@+id/progressbar"
    android:layout_width="250dp"
    android:layout_height="250dp"
    android:padding="10dp"
    app:stroke_width="10dp"
    app:background_stroke_width="10dp"
    app:progress="50"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toBottomOf="@id/title" />
```
supports `max`, `progress`, `progress_color`, `background_color`, `zero_progress_enabled`, `end_cap_visible`, `end_cap_size`, `stroke_width`, `background_stroke_width`, `animation_duration`, `style` (full, half), `background_style` (full, dotted) params

## License
```
MIT License
Copyright (c) 2020 QusionDev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

        
