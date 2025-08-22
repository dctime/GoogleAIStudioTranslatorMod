# GeminiTranslator

Welcome to **GeminiTranslator Mod**!  
This mod integrates Google Gemini API into Minecraft to provide real-time translation.

---

## Installation

This template repository can be directly cloned to start a new mod project.

1. Go to GitHub and create a new repository using this one as a **Template Repository**:  
   👉 [GitHub Guide](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template)

2. Clone the repository to your local machine and open it in your preferred IDE.
    - Recommended IDE: **IntelliJ IDEA** or **Eclipse**

3. If you encounter missing libraries or build issues, try:
    - `gradlew --refresh-dependencies` → Refresh dependencies
    - `gradlew clean` → Clean the project (does not affect your code)

---

## Mapping Names

By default, this MDK uses the official Mojang mapping names for Minecraft methods and fields.  
These mappings are protected by a license that all developers should be aware of.

For the latest license text, refer to the mapping file itself or see the reference copy here:  
🔗 [Mojang Mapping License](https://github.com/NeoForged/NeoForm/blob/main/Mojang.md)

---

## Additional Resources

- 📖 [NeoForged Documentation](https://docs.neoforged.net/)
- 💬 [NeoForged Discord](https://discord.neoforged.net/)

---

## 使用說明（中文）

1. 請先到 **Google AI Studio** 申請 GeminiTranslator 使用的 **API 金鑰**。
2. 申請完成後，進入 Minecraft 的模組資料夾：  
   minecraft/config/geminitranslator-client

yaml
複製
編輯
3. 使用記事本或文字編輯器打開設定檔，將 **API 金鑰** 填入。
4. 儲存後重新進入遊戲，即可開始使用翻譯功能！

---

### 設定檔範例

```ini
# GeminiTranslator Client Config
apikey=YOUR_API_KEY_HERE
👉 請將 YOUR_API_KEY_HERE 替換成你在 Google AI Studio 申請到的金鑰。

使用示範截圖
以下提供一些使用時的範例截圖（請依實際情況替換）：

📂 Config 設定檔

🎮 遊戲內翻譯效果

Enjoy your Minecraft translation experience with GeminiTranslator! 🚀


---

